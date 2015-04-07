package com.my.project.background;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Flag {
	 
	private Bitmap flagBitmap;
    protected int width = 0;
    protected int height = 0;
    protected int pos_x = 0;
    protected int pos_y = 0;
    protected int numero = -1;
	    
    public	 Flag(Context context, int num) {
    	numero = num;
    	switch(num) {
	    	case Globale.NUM_FLAG_SAFE_ZONE:
	    		flagBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.safe_zone_flag);    		
	    		break;
	    	case Globale.NUM_FLAG_NO_HUNT:
	    		flagBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_hunt_flag);
	    		break;
    	}
        
    }
    
    public	void drawFlag(Canvas canvas, int screen_width, int screen_height) {
 	   if (width == 0 || height == 0) {
 		  height = screen_height/6;
 		  width = (height*flagBitmap.getWidth())/flagBitmap.getHeight();
		  pos_y = 0;
		  
		  switch(numero) {
	    	case Globale.NUM_FLAG_SAFE_ZONE:
	    		pos_x = screen_width/3;    		
	    		break;
	    	case Globale.NUM_FLAG_NO_HUNT:
	    		pos_x = screen_width-50;
	    		break;
  	}
		  
		  flagBitmap = flagBitmap.createScaledBitmap(flagBitmap, width, height, true);
	   }
	   Matrix matrix = new Matrix();
	   matrix.postTranslate(pos_x, pos_y);
	   canvas.drawBitmap(flagBitmap, matrix, null);
  }
    
}
