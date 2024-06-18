package fr.elowyr.dimension.manager;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.dimension.ElowyrDimension;
import fr.elowyr.dimension.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter @Setter
public class EventManager {
    private static @Getter EventManager instance;

    public static final MessageFormat SCORE_FORMAT = new MessageFormat("§f{0}: {1} §e({2,number,#})");
    public static final MessageFormat FINAL_TOP_FORMAT = new MessageFormat("§f{0,choice,1#1ère|1<{0}ème} place: §e{1}");
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss");
    public final Map<Faction, Integer> scores = new ConcurrentHashMap<>();
    public final List<List<String>> rewards = new LinkedList<>();
    private String factionId, playerName;
    public int time;
    private BukkitTask timerTask;
    private boolean finished = true;


    @SuppressWarnings("unchecked")
    public EventManager() {
        instance = this;
        ConfigurationSection section = ElowyrDimension.getInstance().getConfig().getConfigurationSection("event");
        this.rewards.clear();
        this.rewards.addAll((List<List<String>>) section.getList("rewards"));
        registerPlaceHolder();
    }

    public void onStart() {
        this.finished = false;
        this.scores.clear();
        Bukkit.broadcastMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("EVENT.STARTED")));
        this.time = 100;
        timerTask = Bukkit.getScheduler().runTaskTimer(ElowyrDimension.getInstance(), this::tick, 0, 20);
    }

    public void onFinish() {
        this.finished = true;
        List<Map.Entry<Faction, Integer>> scores = this.orderedScores().collect(Collectors.toList());
        if (scores.size() > 0) {
            Bukkit.broadcastMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("EVENT.FINISH")));
            for (int i = 0; i < scores.size() && i < 3; i++) {
                Bukkit.broadcastMessage(FINAL_TOP_FORMAT.format(new Object[]{i + 1, scores.get(i).getKey().getTag()}));
            }
        } else {
            Bukkit.broadcastMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("EVENT.FINISH-NO-FAC")));
        }

        this.setFactionId(null);
        this.setPlayerName(null);
        Optional.ofNullable(this.timerTask).ifPresent(BukkitTask::cancel);
        LinkedList<String> winners = new LinkedList<>();
        for (int i = 0; i < scores.size() && i < this.rewards.size(); i++) {
            Faction faction = scores.get(i).getKey();
            winners.add(faction.getTag());
            String name = faction.getOnlinePlayers()
                    .stream()
                    .findAny()
                    .map(Player::getName)
                    .orElse(""); // if they won... They are online (skipped intensive checks)
            this.rewards.get(i)
                    .stream()
                    .map(s -> s.replaceAll("%faction%", faction.getTag()))
                    .map(s -> s.replaceAll("%player%", name))
                    .forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
        }
        //ElowyrEvents.getInstance().sendResult(this, winners);
    }

    public void tick() {
        time--;
        if (this.time == 0) {
            this.finished = true;
            this.onFinish();
        }
    }

    public void registerPlaceHolder() {
        new PlaceholderExpansion() {

            @Override
            public String getIdentifier() {
                return "eventminage";
            }

            @Override
            public String getAuthor() {
                return "AnZok";
            }

            @Override
            public String getVersion() {
                return "1.0";
            }

            @Override
            public String onPlaceholderRequest(Player player, String params) {
                Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
                List<Map.Entry<Faction, Integer>> bestScores = orderedScores().limit(3).collect(Collectors.toList());
                if (params.equals("time_left")) {
                    return FORMAT.format(getTime() * 1000);
                }
                if (params.equals("score")) {
                    return "" + getScore(faction);
                }

                if (params.contains("isEnable")) {
                    if (!isFinished()) {
                        return "true";
                    } else {
                        return "false";
                    }
                }

                for (int i = 0; i < 3; i++) {
                    if (params.equals("classement_" + i)) {
                        if (bestScores.size() >= i + 1) {
                            Map.Entry<Faction, Integer> score = bestScores.get(i);
                            return SCORE_FORMAT.format(new Object[]{i + 1, score.getKey().getTag(), score.getValue()});
                        } else {
                            return "§c✖";
                        }
                    }
                }

                return super.onPlaceholderRequest(player, params);
            }
        }.register();
    }

    public int getTime() {
        return time;
    }

    public int getScore(Faction faction) {
        return this.scores.getOrDefault(faction, 0);
    }

    public boolean isFinished() {
        return finished;
    }

    public Stream<Map.Entry<Faction, Integer>> orderedScores() {
        return this.scores
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
    }
}
