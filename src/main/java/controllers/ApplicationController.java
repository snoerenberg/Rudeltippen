package controllers;

import java.util.List;

import models.Playday;
import models.Settings;
import models.User;
import models.enums.Constants;
import models.statistic.GameTipStatistic;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import services.CalculationService;
import services.DataService;
import services.I18nService;
import services.StatisticService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 
 * @author svenkubiak
 *
 */
@Singleton
public class ApplicationController extends RootController {

    @Inject
    private DataService dataService;
    
    @Inject
    private StatisticService statisticService;
    
    @Inject
    private CalculationService calculationService;

    @Inject
    private I18nService i18nService;

    public Result index(Context context) {
        final int pointsDiff = calculationService.getPointsToFirstPlace(context.getAttribute(Constants.CONNECTEDUSER.value(), User.class));
        final String diffToTop = i18nService.getDiffToTop(pointsDiff);
        final Playday playday = dataService.findCurrentPlayday();
        final List<User> topUsers = dataService.findTopThreeUsers();
        final long users = dataService.countAllUsers();
        
        return Results.html()
                .render("topUsers", topUsers)
                .render("playday", playday)
                .render("users", users)
                .render("diffToTop", diffToTop);
    }

    public Result rules() {
        Settings settings = dataService.findSettings();
        return Results.html().render(settings);
    }

    public Result statistics() {
        final List<Object[]> games = statisticService.getGameStatistics();
        final List<Object[]> results = statisticService.getResultsStatistic();
        final List<GameTipStatistic> gameTipStatistics = dataService.findGameTipStatisticsOrderByPlayday();

        return Results.html()
                .render("results", results)
                .render("gameTipStatistics", gameTipStatistics)
                .render("games", games);
    }
}