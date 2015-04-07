package com.my.project.models;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;


public class Points {
	private int	score = 0;
	private int	score_tmp = 0;
	protected int width = 0;
    protected int height = 0;
    protected int pos_x = 0;
    protected int pos_y = 0;
    protected Typeface tf;
	
	public Points(Context context) {
		tf =  Typeface.createFromAsset(context.getAssets(), Globale.CUSTOM_FONT);
		tf = tf.create(tf, Typeface.BOLD);
	}
	
	public	void Draw_score(Canvas canvas, int screen_width, int screen_height) {
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
	   if (width == 0 || height == 0) {
		   height = screen_height/12;
		   width = height*3;
		   
		   pos_y = 5;
		   pos_x = screen_width/2;
		   
		   //Typeface.create("Chicken_Butt", Typeface.BOLD);
	   }
	   paint.setColor(Color.rgb(160, 90, 44));
	   paint.setTextSize(height);
	   paint.setTypeface(tf);
	   
	   if (score < score_tmp) {
		   score += 10;
		   if (score > score_tmp)
			   score = score_tmp;
	   }
	   
		   matrix.postTranslate(pos_x, pos_y);
		   //canvas.drawBitmap(logo, matrix, null);
		   canvas.drawText("Score: ", pos_x, height, paint);
		   canvas.drawText(Integer.toString(score), pos_x + width, height, paint);
	}



	public void addScore(int score) {
		this.score_tmp += score;
	}
}
