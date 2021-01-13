package com.example.thenewnumberourdays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class CountdownWidget extends AppWidgetProvider {

    @RequiresApi(api = Build.VERSION_CODES.O)
    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        final CharSequence widgetText = calculateRemainingTime(LocalDateTime.now(), LocalDateTime.of(2024,5,11,3,42));
        // Construct the RemoteViews object

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String calculateRemainingTime(LocalDateTime start, LocalDateTime end)
    {
        //calculate each unit of time, then subtract that unit from the end before calculating the next
        long years = ChronoUnit.YEARS.between(start, end);
        LocalDateTime newEnd = end.minusYears(years);

        long days = ChronoUnit.DAYS.between(start, newEnd);
        newEnd = newEnd.minusDays(days);

        long hours = ChronoUnit.HOURS.between(start, newEnd);

        return String.format(Locale.getDefault(), "%02d Years, %02d Days, %02d Hours", years, days, hours);
    }
}

