package com.my.project.background;

import java.io.InputStream;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Background {

	private Bitmap fontBitmap;
	protected int width = 0;
    protected int height = 0;
    protected int pos_x = 0;
    protected int pos_y = 0;
    private Flag safe_zone;
    private Flag no_hunt;
    private Front avant_plan;
   
   public	Background(Context context) {
	   //fontBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bad_font);
	   InputStream x = context.getResources().openRawResource(R.drawable.water_font);
	   fontBitmap = BitmapFactory.decodeStream(x);
	   
	   
	   safe_zone = new Flag(context, Globale.NUM_FLAG_SAFE_ZONE);
	   no_hunt = new Flag(context, Globale.NUM_FLAG_NO_HUNT);
	   avant_plan = new Front(context);
   }
    
   public	void drawBackground(Canvas canvas, int screen_width, int screen_height) {
	   if (width == 0 || height == 0) {
		   width = screen_width;
		   height = screen_height;
		   fontBitmap = fontBitmap.createScaledBitmap(fontBitmap, width, height, true);
	   }
	   Matrix matrix = new Matrix();
	   matrix.postTranslate(pos_x, pos_y);
	   canvas.drawBitmap(fontBitmap, matrix, null);
	   
	   avant_plan.drawFront(canvas, screen_width, screen_height);
	   safe_zone.drawFlag(canvas, screen_width, screen_height);
	   no_hunt.drawFlag(canvas, screen_width, screen_height);
   }
}
