package services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import models.AbstractJob;
import models.Bracket;
import models.Confirmation;
import models.ConfirmationType;
import models.Constants;
import models.Extra;
import models.ExtraTip;
import models.Game;
import models.GameTip;
import models.Playday;
import models.Settings;
import models.Team;
import models.User;
import models.WSResults;
import models.statistic.GameStatistic;
import models.statistic.GameTipStatistic;
import models.statistic.PlaydayStatistic;
import models.statistic.ResultStatistic;
import models.statistic.UserStatistic;
import ninja.cache.NinjaCache;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;

@Singleton
public class DataService {
    private static final Logger LOG = LoggerFactory.getLogger(DataService.class);
    private static final String PACKAGE = "models";
    private static final String DB = "rudeltippen";
    private Datastore datastore;

    @Inject
    private NinjaCache ninjaCache;

    public DataService() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            LOG.error("Failed to connect to mongo db", e);
        }

        if (mongoClient != null) {
            this.datastore = new Morphia().mapPackage(PACKAGE).createDatastore(mongoClient, DB);
            LOG.info("Created DataStore for MongoDB: " + DB);
        } else {
            LOG.error("Failed to created morphia instance");
        }
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.datastore = new Morphia().mapPackage(PACKAGE).createDatastore(mongoClient, DB);
    }

    public void save(Object object) {
        this.datastore.save(object);
    }

    public void delete(Object object) {
        this.datastore.delete(object);
    }

    public void deleteAll(final Class<?> clazz) {
        this.datastore.delete(this.datastore.createQuery(clazz));
    }

    public AbstractJob findAbstractJobByName(String jobName) {
        return this.datastore.find(AbstractJob.class).field("name").equal(jobName).get();
    }

    public List<Confirmation>  findAllPendingActivatations() {
        //TODO Refactoring
        //Confirmation.find("SELECT c FROM Confirmation c WHERE confirmType = ? AND DATE(NOW()) > (DATE(created) + 2)", ConfirmationType.ACTIVATION).fetch();

        return null;
    }

    public List<User> findAllNotifiableUsers() {
        return this.datastore.find(User.class).field("active").equal(true).field("sendGameTips").equal(true).asList();
    }

    public List<Game> findAllNotifiableGames() {
        //TODO Refactoring
        //Game.find("SELECT g FROM Game g WHERE informed = ? AND ( TIMESTAMPDIFF(MINUTE,kickoff,now()) > 1 )", false).fetch();
        return null;
    }

    public Playday findPlaydaybByNumber(int number) {
        return this.datastore.find(Playday.class).field("number").equal(number).get();
    }

    public List<Game> findAllGamesWithNoResult() {
        //TODO Refactoring
        // Game.find("SELECT g FROM Game g WHERE ended != 1 AND ( TIMESTAMPDIFF(MINUTE,kickoff,now()) > 90 ) AND homeTeam_id != '' AND awayTeam_id != '' AND webserviceID != ''").fetch();
        return null;
    }

    public List<Extra> findAllExtrasEndingToday() {
        //TODO Refactoring
        // Extra.find("SELECT e FROM Extra e WHERE DATE(ending) = DATE(NOW())").fetch();
        return null;
    }

    public List<Game> findAllGamesEndingToday() {
        //TODO Refactoring
        // Game.find("SELECT g FROM Game g WHERE DATE(kickoff) = DATE(NOW())").fetch();
        return null;
    }

    public List<User> findAllRemindableUsers() {
        return this.datastore.find(User.class).field("reminder").equal(true).field("active").equal(true).asList();
    }

    public GameTip findGameTipByGameAndUser(User user, Game game) {
        //TODO Refactoring
        // GameTip.find("byGameAndUser", game, user).first();
        return null;
    }

    public ExtraTip findExtraTipByExtraAndUser(Extra extra, User user) {
        //TODO Refactoring
        // ExtraTip.find("byExtraAndUser", extra, user).first();
        return null;
    }

    public List<User> findAllAdmins() {
        return this.datastore.find(User.class).field("admin").equal(true).asList();
    }

    public List<User> findUsersByNotificationAndActive() {
        return this.datastore.find(User.class).field("active").equal(true).field("notification").equal(true).asList();
    }

    public Game findGameFirstGame() {
        return this.datastore.find(Game.class).field("number").equal(1).get();
    }

    public List<User> findTopThreeUsers() {
        return this.datastore.find(User.class).field("active").equal(true).order("place").limit(3).asList();
    }

    public List<User> findSendableUsers() {
        return this.datastore.find(User.class).field("sendStandings").equal(true).asList();
    }

    public List<GameTip> findGameTipByUser(User user) {
        //TODO Refactoring
        //GameTip.find("byUser", user).fetch();
        return null;
    }

    public ResultStatistic findResultStatisticByUserAndResult(User user, String score) {
        //TODO Refactoring
        //ResultStatistic.find("byUserAndResult", user, score).first();
        return null;
    }

    public GameStatistic findGameStatisticByPlaydayAndResult(Playday playday, Object key) {
        //TODO Refactoring
        //GameStatistic.find("byPlaydayAndGameResult", playday, entry.getKey()).first();
        return null;
    }

    public GameTipStatistic findGameTipStatisticByPlayday() {
        //TODO Refactoring
        // GameTipStatistic.find("byPlayday", playday).first();
        return null;
    }

    public UserStatistic findUserStatisticByPlaydayAndUser(Playday playday, User user) {
        //TODO Refactoring
        // UserStatistic.find("byPlaydayAndUser", playday, user).first();
        return null;
    }

    public List<UserStatistic> findUserStatisticByPlaydayOrderByPlaydayPoints(Playday playday) {
        //TODO Refactoring
        // UserStatistic.find("SELECT u FROM UserStatistic u WHERE playday = ? ORDER BY playdayPoints DESC", playday).fetch();
        return null;
    }

    public List<UserStatistic> findUserStatisticByPlaydayOrderByPoints(Playday playday) {
        //TODO Refactoring
        //  UserStatistic.find("SELECT u FROM UserStatistic u WHERE playday = ? ORDER BY points DESC", playday).fetch();
        return null;
    }

    public List<GameTip> findGameTipByGame(Game game) {
        //TODO Refactoring
        // GameTip.find("byGame", game).fetch();
        return null;
    }

    public PlaydayStatistic findPlaydayStatisticByPlaydayAndResult(Playday playday, Object key) {
        //TODO Refactoring
        // PlaydayStatistic.find("byPlaydayAndGameResult", playday, entry.getKey()).first();
        return null;
    }

    public User findUserByEmail(String email) {
        return this.datastore.find(User.class).field("email").equal(email).get();
    }

    public User findUserByUsername(String username) {
        return this.datastore.find(User.class).field("username").equal(username).get();
    }

    public List<Confirmation> findAllConfirmation() {
        return this.datastore.find(Confirmation.class).asList();
    }

    public List<User> getAllActiveUsers() {
        return this.datastore.find(User.class).field("active").equal(true).asList();
    }

    public Settings findSettings() {
        Settings settings = (Settings) ninjaCache.get("settings");
        if (settings == null) {
            settings = this.datastore.find(Settings.class).field("appName").equal(Constants.APPNAME.value()).get();
            ninjaCache.add("settings", settings);
        }

        return settings;
    }

    public int getPointsToFirstPlace() {
        //TODO Refactoring
        //        final User connectedUser = AppUtils.getConnectedUser();
        //        final User user = User.find("byPlace", 1).first();
        //
        //        int pointsDiff = 0;
        //        if (user != null && connectedUser != null) {
        //            pointsDiff = user.getPoints() - connectedUser.getPoints();
        //        }
        //
        //        return pointsDiff;
        return 0;
    }

    public User getConnectedUser() {
        //TODO Refactoring
        //        final String username = Security.connected();
        //        User connectedUser = null;
        //        if (StringUtils.isNotBlank(username)) {
        //            connectedUser = User.find("SELECT u FROM User u WHERE active = true AND username = ? OR email = ?", username, username).first();
        //        }
        //
        //        return connectedUser;
        return null;
    }

    public boolean isJobInstance() {
        //TODO Refactoring
        //        boolean isInstance = false;
        //        final String appName = Play.configuration.getProperty("application.name");
        //        final String jobInstance = Play.configuration.getProperty("app.jobinstance");
        //        if (StringUtils.isNotBlank(appName) && StringUtils.isNotBlank(jobInstance) && appName.equalsIgnoreCase(jobInstance)) {
        //            isInstance = true;
        //        }
        //
        //        return isInstance;

        return false;
    }
    public Team getTeamByReference(final String reference) {
        // TODO Refactoring
        //        Team team = null;
        //        if (StringUtils.isNotBlank(reference)) {
        //            final String[] references = reference.split("-");
        //            if ((references != null) && (references.length == 3)) {
        //                if ("B".equals(references[0])) {
        //                    final Bracket bracket = Bracket.find("byNumber", Integer.parseInt(references[1])).first();
        //                    if (bracket != null) {
        //                        team = bracket.getTeamByPlace(Integer.parseInt(references[2]));
        //                    }
        //                } else if ("G".equals(references[0])) {
        //                    final Game aGame = Game.find("byNumber", Integer.parseInt(references[1])).first();
        //                    if ((aGame != null) && aGame.isEnded()) {
        //                        if ("W".equals(references[2])) {
        //                            team = aGame.getWinner();
        //                        } else if ("L".equals(references[2])) {
        //                            team = aGame.getLoser();
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //
        //        return team;
        return null;
    }

    public void saveScore(final Game game, final String homeScore, final String awayScore, final String extratime, String homeScoreExtratime, String awayScoreExtratime) {
        //TODO Refactoring
        //        final int[] points = AppUtils.getPoints(Integer.parseInt(homeScore), Integer.parseInt(awayScore));
        //        game.setHomePoints(points[0]);
        //        game.setAwayPoints(points[1]);
        //        game.setHomeScore(homeScore);
        //        game.setAwayScore(awayScore);
        //        if (ValidationService.isValidScore(homeScoreExtratime, awayScoreExtratime )) {
        //            homeScoreExtratime = homeScoreExtratime.trim();
        //            awayScoreExtratime = awayScoreExtratime.trim();
        //            game.setOvertimeType(extratime);
        //            game.setHomeScoreOT(homeScoreExtratime);
        //            game.setAwayScoreOT(awayScoreExtratime);
        //            game.setOvertime(true);
        //        } else {
        //            game.setOvertime(false);
        //        }
        //
        //        if (!game.isEnded()) {
        //            NotificationService.sendNotfications(game);
        //            game.setEnded(true);
        //        }
        //        game._save();
    }

    public int getTipPoints(final int homeScore, final int awayScore, final int homeScoreTipp, final int awayScoreTipp) {
        //TODO Refactrogin
        //        final Settings settings = AppUtils.getSettings();
        //        int points = 0;
        //
        //        if ((homeScore == homeScoreTipp) && (awayScore == awayScoreTipp)) {
        //            points = settings.getPointsTip();
        //        } else if ((homeScore - awayScore) == (homeScoreTipp - awayScoreTipp)) {
        //            points = settings.getPointsTipDiff();
        //        } else if ((awayScore - homeScore) == (awayScoreTipp - homeScoreTipp)) {
        //            points = settings.getPointsTipDiff();
        //        } else {
        //            points = getTipPointsTrend(homeScore, awayScore, homeScoreTipp, awayScoreTipp);
        //        }
        //
        //        return points;
        return 0;
    }

    public int getTipPointsTrend(final int homeScore, final int awayScore, final int homeScoreTipp, final int awayScoreTipp) {
        //TODO Refactoring
        //        final Settings settings = AppUtils.getSettings();
        //        int points = 0;
        //
        //        if ((homeScore > awayScore) && (homeScoreTipp > awayScoreTipp)) {
        //            points = settings.getPointsTipTrend();
        //        } else if ((homeScore < awayScore) && (homeScoreTipp < awayScoreTipp)) {
        //            points = settings.getPointsTipTrend();
        //        }
        //
        //        return points;
        return 0;
    }

    public int getTipPointsOvertime(final int homeScore, final int awayScore, final int homeScoreOT, final int awayScoreOT, final int homeScoreTipp, final int awayScoreTipp) {
        //TODO Refacttoring
        //        final Settings settings = AppUtils.getSettings();
        //        int points = 0;
        //
        //        if (settings.isCountFinalResult()) {
        //            points = getTipPoints(homeScoreOT, awayScoreOT, homeScoreTipp, awayScoreTipp);
        //        } else {
        //            if ((homeScore == awayScore) && (homeScore == homeScoreTipp) && (awayScore == awayScoreTipp)) {
        //                points = settings.getPointsTip();
        //            } else if ((homeScore == awayScore) && (homeScoreTipp == awayScoreTipp)) {
        //                points = settings.getPointsTipDiff();
        //            }
        //        }
        //
        //        return points;
        return 0;
    }

    public int[] getPoints(final int homeScore, final int awayScore) {
        //TODO Refactoring
        //        final Settings settings = AppUtils.getSettings();
        //        final int[] points = new int[2];
        //
        //        if (homeScore == awayScore) {
        //            points[0] = settings.getPointsGameDraw();
        //            points[1] = settings.getPointsGameDraw();
        //        } else if (homeScore > awayScore) {
        //            points[0] = settings.getPointsGameWin();
        //            points[1] = 0;
        //        } else if (homeScore < awayScore) {
        //            points[0] = 0;
        //            points[1] = settings.getPointsGameWin();
        //        }
        //
        //        return points;
        return null;
    }

    public void placeTip(final Game game, final int homeScore, final int awayScore) {
        //TODO Refactoring
        //        final User user = AppUtils.getConnectedUser();
        //        GameTip gameTip = GameTip.find("byUserAndGame", user, game).first();
        //        if (game.isTippable() && ValidationService.isValidScore(String.valueOf(homeScore), String.valueOf(awayScore))) {
        //            if (gameTip == null) {
        //                gameTip = new GameTip();
        //                gameTip.setGame(game);
        //                gameTip.setUser(user);
        //            }
        //            gameTip.setPlaced(new Date());
        //            gameTip.setHomeScore(homeScore);
        //            gameTip.setAwayScore(awayScore);
        //            gameTip._save();
        //            Logger.info("Tipp placed - " + user.getEmail() + " - " + gameTip);
        //        }
    }

    public List<Map<User, List<GameTip>>> getPlaydayTips(final Playday playday, final List<User> users) {
        //TODO Refactoring
        //        final List<Map<User, List<GameTip>>> tips = new ArrayList<Map<User, List<GameTip>>>();
        //
        //        for (final User user : users) {
        //            final Map<User, List<GameTip>> userTips = new HashMap<User, List<GameTip>>();
        //            final List<GameTip> gameTips = new ArrayList<GameTip>();
        //            for (final Game game : playday.getGames()) {
        //                GameTip gameTip = GameTip.find("byGameAndUser", game, user).first();
        //                if (gameTip == null) {
        //                    gameTip = new GameTip();
        //                }
        //                gameTips.add(gameTip);
        //            }
        //            userTips.put(user,  gameTips);
        //            tips.add(userTips);
        //        }
        //
        //        return tips;
        return null;
    }
    public List<Map<User, List<ExtraTip>>> getExtraTips(final List<User> users, final List<Extra> extras) {
        //TODO Refactoring
        //        final List<Map<User, List<ExtraTip>>> tips = new ArrayList<Map<User, List<ExtraTip>>>();
        //
        //        for (final User user : users) {
        //            final Map<User, List<ExtraTip>> userTips = new HashMap<User, List<ExtraTip>>();
        //            final List<ExtraTip> extraTips = new ArrayList<ExtraTip>();
        //            for (final Extra extra : extras) {
        //                ExtraTip extraTip = ExtraTip.find("byExtraAndUser", extra, user).first();
        //                if (extraTip == null) {
        //                    extraTip = new ExtraTip();
        //                }
        //                extraTips.add(extraTip);
        //            }
        //            userTips.put(user, extraTips);
        //            tips.add(userTips);
        //        }
        //
        //        return tips;
        return null;
    }

    public Playday getCurrentPlayday () {
        //TODO Refactoring
        //        Playday playday = Playday.find("byCurrent", true).first();
        //        if (playday == null) {
        //            playday = Playday.find("byNumber", 1).first();
        //        }
        //
        //        return playday;
        return null;
    }

    public void setGameScoreFromWebService(final Game game, final WSResults wsResults) {
        //TODO Refactoring
        //        final Map<String, WSResult> wsResult = wsResults.getWsResult();
        //
        //        String homeScore = null;
        //        String awayScore = null;
        //        String homeScoreExtratime = null;
        //        String awayScoreExtratime = null;
        //        String extratime = null;
        //
        //        if (wsResult.containsKey("90")) {
        //            homeScore = wsResult.get("90").getHomeScore();
        //            awayScore = wsResult.get("90").getAwayScore();
        //        }
        //
        //        if (wsResult.containsKey("121")) {
        //            homeScoreExtratime = wsResult.get("121").getHomeScore();
        //            awayScoreExtratime = wsResult.get("121").getAwayScore();
        //            extratime = "ie";
        //        } else if (wsResult.containsKey("120")) {
        //            homeScoreExtratime = wsResult.get("120").getHomeScore();
        //            awayScoreExtratime = wsResult.get("120").getAwayScore();
        //            extratime = "nv";
        //        }
        //
        //        Logger.info("Recieved from WebService - HomeScore: " + homeScore + " AwayScore: " + awayScore);
        //        Logger.info("Recieved from WebService - HomeScoreExtra: " + homeScoreExtratime + " AwayScoreExtra: " + awayScoreExtratime + " (" + extratime + ")");
        //        Logger.info("Updating results from WebService. " + game);
        //        setGameScore(String.valueOf(game.getId()), homeScore, awayScore, extratime, homeScoreExtratime, awayScoreExtratime);
        //        calculations();
    }

    public void placeExtraTip(final Extra extra, final Team team) {
        //TODO Refactoring
        //        final User user = AppUtils.getConnectedUser();
        //        if (team != null) {
        //            ExtraTip extraTip = ExtraTip.find("byUserAndExtra", user, extra).first();
        //            if (extraTip == null) {
        //                extraTip = new ExtraTip();
        //            }
        //
        //            extraTip.setUser(user);
        //            extraTip.setExtra(extra);
        //            extraTip.setAnswer(team);
        //            extraTip._save();
        //            Logger.info("Stored extratip - " + user.getEmail() + " - " + extraTip);
        //        }
    }

    public boolean appIsInizialized() {
        return findSettings() != null ? true : false;
    }

    public User connectUser(final String username, final String userpass) {
        //TODO Refactoring
        //        User user = User.find("byUsernameAndUserpassAndActive", username, userpass, true).first();
        //        if (user == null) {
        //            user = User.find("byEmailAndUserpassAndActive", username, userpass, true).first();
        //        }
        //
        //        return user;
        return null;
    }

    public List<User> findAllActiveUsers() {
        return this.datastore.find(User.class).field("active").equal(true).asList();
    }

    public List<Playday> findAllPlaydaysOrderByNumber() {
        return this.datastore.find(Playday.class).order("number").asList();
    }

    public List<Extra> findAllExtras() {
        return this.datastore.find(Extra.class).asList();
    }

    public List<Team> findAllTeams() {
        return this.datastore.find(Team.class).asList();
    }

    public List<Game> findGamesByHomeTeam(Team team) {
        //TODO Refactoring
        return null;
    }

    public List<Game> findGamesByAwayTeam(Team team) {
        //TODO Refactoring
        return null;
    }

    public List<User> findAllActiveUsersOrdered() {
        //TODO Refactoring
        //User.find("SELECT u FROM User u WHERE active = true ORDER BY points DESC, correctResults DESC, correctDifferences DESC, correctTrends DESC, correctExtraTips DESC").fetch();
        return null;
    }

    public List<Bracket> findAllBrackets() {
        return this.datastore.find(Bracket.class).asList();
    }

    public Game findGameById(long parseLong) {
        // TODO Refactoring
        return null;
    }

    public List<Bracket> findAllUpdatableBrackets() {
        return this.datastore.find(Bracket.class).field("updatable").equal(true).asList();
    }

    public List<Team> findTeamsByBracket(Bracket bracket) {
        // TODO Refactoring
        //Team.find("SELECT t FROM Team t WHERE bracket_id = ? ORDER BY points DESC, goalsDiff DESC, goalsFor DESC", bracket.getId()).fetch();
        return null;
    }

    public List<Game> findGamesByPlayoffAndEndedAndBracket() {
        // TODO Refactoring
        // Game.find("byPlayoffAndEndedAndBracket", true, false, null).fetch();
        return null;
    }

    public List<Game> findReferencedGames() {
        // TODO Refactoring
        // Game.find("SELECT g FROM Game g WHERE homeReference LIKE ? OR awayReference LIKE ?", bracketString, bracketString).fetch();
        return null;
    }

    public List<Game> findAllNonPlayoffGames() {
        return this.datastore.find(Game.class).field("playoff").equal(false).asList();
    }

    public List<Game> findAllPlayoffGames() {
        return this.datastore.find(Game.class).field("playoff").equal(true).asList();
    }

    public long getUsersCount() {
        return this.datastore.find(User.class).countAll();
    }

    public List<User> findActiveUsers(int limit) {
        return this.datastore.find(User.class).field("active").equal(true).order("place").limit(limit).asList();
    }

    public List<User> findAllActiveUsersOrderedByPlace() {
        return this.datastore.find(User.class).field("active").equal(true).order("place").asList();
    }

    public Team findTeamById(long teamid) {
        // TODO Refactoring
        return null;
    }

    public Bracket findBracketById(long bracketid) {
        // TODO Refactoring
        return null;
    }

    public Extra findExtaById(Long bonusTippId) {
        // TODO Refactoring
        return null;
    }

    public long getExtrasCount() {
        return this.datastore.find(Extra.class).countAll();
    }

    public long getGameCount() {
        return this.datastore.find(Game.class).countAll();
    }

    public List<User> findUsersOrderByUsername() {
        return this.datastore.find(User.class).field("active").equal(true).order("username").asList();
    }

    public User findUserById(long userid) {
        // TODO Refactoring
        return null;
    }

    public Confirmation findConfirmationByTypeAndUser(ConfirmationType confirmationType, User user) {
        // TODO refactoring
        // Confirmation.find("byConfirmTypeAndUser", , user).first();
        return null;
    }

    public List<Game> findAllGames() {
        return this.datastore.find(Game.class).asList();
    }
}