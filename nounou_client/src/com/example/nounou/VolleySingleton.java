package com.example.nounou;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
	 
    private static VolleySingleton INSTANCE = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
 
    private VolleySingleton(Context context) {
    	//Nouvelle file d'attente de requetes volley
        requestQueue = Volley.newRequestQueue(context);
        //Image loader pour telecharger les images des nounous
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
 
    //getInstance pour le Singeton
    public static VolleySingleton getInstance(Context contexte) {
    	if (INSTANCE == null) {
			/**Synchronized permet de partager l'instance entre les threads*/
			synchronized (VolleySingleton.class) {
				if (INSTANCE == null) {
					INSTANCE = new VolleySingleton(contexte);
				}
			}
		}
		return INSTANCE;
    }
 
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
 
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
