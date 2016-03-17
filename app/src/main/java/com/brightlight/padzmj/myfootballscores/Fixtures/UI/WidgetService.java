package com.brightlight.padzmj.myfootballscores.Fixtures.UI;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by PadzMJ on 08/03/2016.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        WidgetDataProvider widgetDataProvider = new WidgetDataProvider(getApplicationContext(), intent);
        return widgetDataProvider;
    }
}
