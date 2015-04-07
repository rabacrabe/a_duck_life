package com.my.project.background;

import java.io.InputStream;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Front {
	 private Bitmap avant_planBitmap;
	 protected int width = 0;
	    protected int height = 0;
	    protected int pos_x = 0;
	    protected int pos_y = 0;
	 
	 public	Front(Context context) {
		// avant_planBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.herbe_haute_icon);
		 InputStream x = context.getResources().openRawResource(R.drawable.herbe_haute_icon);
		 avant_planBitmap = BitmapFactory.decodeStream(x);
	 }
	 
	 public	void drawFront(Canvas canvas, int screen_width, int screen_height) {
		   Matrix matrix = new Matrix();
		   if (width == 0 || height == 0) {
			   width = screen_width;
			   height = screen_height/Globale.SIZE_FRONT_BOTTOM;
			   pos_y = screen_height-height;
			 
			   avant_planBitmap = avant_planBitmap.createScaledBitmap(avant_planBitmap, width, height, true);
		   }
		   
		   matrix.postTranslate(pos_x, pos_y);
		   canvas.drawBitmap(avant_planBitmap, matrix, null);
	   }
}
