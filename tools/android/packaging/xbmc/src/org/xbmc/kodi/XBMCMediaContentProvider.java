package org.xbmc.kodi;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class XBMCMediaContentProvider extends ContentProvider
{
  private static String TAG = "kodimediaprovider";
  
  public static final String AUTHORITY = "org.xbmc.kodi";
  public static final String AUTHORITY_MEDIA = AUTHORITY + ".media";
  public static final String SUGGEST_PATH = "suggestions";

  // UriMatcher stuff
  private static final int SEARCH_SUGGEST = 0;
  private static final int REFRESH_SHORTCUT = 1;
  private static final UriMatcher URI_MATCHER = buildUriMatcher();

  private XBMCJsonRPC mJsonRPC = null;

  private static UriMatcher buildUriMatcher()
  {
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    matcher.addURI(AUTHORITY_MEDIA, SUGGEST_PATH + "/" + SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
    matcher.addURI(AUTHORITY_MEDIA, SUGGEST_PATH + "/" + SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
    return matcher;
  }

  @Override
  public int delete(Uri arg0, String arg1, String[] arg2)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getType(Uri arg0)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Uri insert(Uri arg0, ContentValues arg1)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean onCreate()
  {
    mJsonRPC  = new XBMCJsonRPC();

    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
          String[] selectionArgs, String sortOrder)
  {
    Log.d(TAG, "query: " + uri.toString());
    
    switch (URI_MATCHER.match(uri))
    {
    case SEARCH_SUGGEST:
      String query = uri.getLastPathSegment().toLowerCase();
      return mJsonRPC.getSuggestions(query);
      
    default:
      throw new IllegalArgumentException("Unknown Uri: " + uri);
    }
  }

  @Override
  public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3)
  {
    // TODO Auto-generated method stub
    return 0;
  }

}
