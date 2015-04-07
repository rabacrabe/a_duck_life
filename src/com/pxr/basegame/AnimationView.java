package com.pxr.basegame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.http.impl.cookie.BasicClientCookie;

import com.my.project.background.Background;
import com.my.project.models.ColorButton;
import com.my.project.models.HeartLife;
import com.my.project.models.Points;
import com.my.project.models.Target;
import com.my.project.models.ducks.Basic_Duck;
import com.my.project.models.targets.Bomb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.SystemClock;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class AnimationView extends SurfaceView implements SurfaceHolder.Callback {
	
	private Context myContext;

	class AnimationThread extends Thread {
    	
    	/** Are we running ? */
    	private boolean mRun;
    	
        /** Used to figure out elapsed time between frames */
        private long mLastTime;      
        
        /** Variables for the counter */
        private int frameSamplesCollected = 0;
        private int frameSampleTime = 0;
        private int fps = 0;
        
        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;

        /** How to display the text */
        private Paint textPaint;
                
        //private Bitmap batBitmap;
        //private Bitmap fontBitmap;
        private Bitmap avant_planBitmap;
        private Bitmap flag_safe_zoneBitmap;
        private Bitmap flag_no_huntBitmap;
        
        private int batAngle = 0;
        private	int batWidth;
        private int batHeight;
        private	int batX;
        private int batY;
        private int screenWidth;
        private int screenHeight;
        
        private	float backgroundX = 0;
        private	float backgroundY = 0;
        
        private Background background;
        
        
        
        
        public AnimationThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
                        
          // batBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
           //fontBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bad_font);
           
           background = new Background(myContext);
           
          // batWidth = batBitmap.getWidth();
          // batHeight = batBitmap.getHeight();
           
           InitColorButtons();
           InitHeartLife();
           
           initTargets();
           
            /** Initiate the text painter */
            textPaint = new Paint();
            textPaint.setARGB(255,255,255,255);
            textPaint.setTextSize(32);
        }

        public	void initTargets(){
        	//creation d'une cible
            targetList = Collections.synchronizedList(new ArrayList<Target>());
            targetList.add(new Basic_Duck(myContext, target_id));
            target_id++;
         /*
            targetList.add(new Baloon(myContext, target_id));
            target_id++;
            targetList.add(new Baloon(myContext, target_id));
            target_id++;
            */
        }
        
        public void InitColorButtons() {
        	buttonList = new ArrayList<ColorButton>();
        	buttonList.add(new ColorButton(myContext, Globale.BLUE_ID));
        	buttonList.add(new ColorButton(myContext, Globale.GREEN_ID));
        	buttonList.add(new ColorButton(myContext, Globale.ORANGE_ID));
        	buttonList.add(new ColorButton(myContext, Globale.PURPLE_ID));
        	buttonList.add(new ColorButton(myContext, Globale.RED_ID));
        }
        
        private void InitHeartLife() {
        	lifeList = new ArrayList<HeartLife>();
        	for (int i = 0; i < Globale.NB_HEART_LIFE; i++) {
        		lifeList.add(new HeartLife(myContext, i));        		
        	}
        }
        
        /**
         * The actual game loop!
         */
        @Override
        public void run() {
        	batY = 0;
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    screenHeight = c.getHeight();
                    screenWidth = c.getWidth();
                    
                    VerifSimpletap();
                    
                    //batX = (screenWidth/2)-(batWidth/2);
                    //batY = (screenHeight/2)-(batHeight/2);
                    synchronized (mSurfaceHolder) {   
                    	updatePhysics();
                        doDraw(c);
                    }
                }finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
        
        /**
         * Figures the gamestate based on the passage of
         * realtime. Called at the start of draw().
         * Only calculates the FPS for now.
         */
        private void updatePhysics() {
            long now = System.currentTimeMillis();
                        
            if (mLastTime != 0) {
            	
            	//Time difference between now and last time we were here
        		int time = (int) (now - mLastTime);
        		frameSampleTime += time;
        		frameSamplesCollected++;
        		
        		//After 10 frames
        		if (frameSamplesCollected == 10) {
        			
        			//Update the fps variable
	        		fps = (int) (10000 / frameSampleTime);
	        		
	        		//Reset the sampletime + frames collected
	        		frameSampleTime = 0;
	        		frameSamplesCollected = 0;
        		}        		
        	}
            
            mLastTime = now;
        }
        
        private	void	drawBat(Canvas canvas) {
        	
        	ArrayList<Target> newTargets = new ArrayList<Target>();
        	HashMap<String, Integer> shoot;
        	synchronized (targetList) {
        		for (Iterator<Target> iter = targetList.iterator(); iter.hasNext();) {
	    	    	Target target = (Target) iter.next();
	    	    	
	    	    	if (target.GetIsToDel() == true) {
	    	    		score.addScore(target.getScore());
	    	    		iter.remove();
	    	    		Basic_Duck basic_duck = new Basic_Duck(myContext, target_id);
	    	    		newTargets.add(basic_duck);
	    	    		target_id++;
	    	    		basic_duck = new Basic_Duck(myContext, target_id);
	    	    		newTargets.add(basic_duck);
	    	    		target_id++;
	    	    	}
	    	    	else {
	    	    		//Log.v("target id", ""+target_id_focus+" "+target.getId());
	    	    		if (target_id_focus == target.getId())
	    	    			target.drawTarget(canvas, screenWidth, screenHeight, true);
	    	    		else
	    	    			target.drawTarget(canvas, screenWidth, screenHeight, false);
	    	    	}
	    	    	if ((shoot = target.shootBomb()) != null) {
	    	    		Bomb newBomb = new Bomb(myContext, target_id);
	    	    		newBomb.setPos_x(shoot.get("x"));
	    	    		newBomb.setPos_y(shoot.get("y"));
	    	    		newTargets.add(newBomb);
	    	    		target_id++;
	    	    	}
        		}
        		for (Target bomb : newTargets){
        			targetList.add(bomb);
        		}
			}
        }
        
        private void drawBackground(Canvas canvas) {
        	/*
        	Matrix matrix = new Matrix();
        	
        	 fontBitmap = fontBitmap.createScaledBitmap(
                     fontBitmap, screenWidth, screenHeight, true);

        	 canvas.drawBitmap(fontBitmap, matrix, null);
        	 */
        	canvas.drawColor(Color.WHITE);
        	background.drawBackground(canvas, screenWidth, screenHeight);
        	
       
        }
        
        private void drawButtons(Canvas canvas) {
        	for (ColorButton button : buttonList) {
        		button.drawButton(canvas, screenWidth, screenHeight);
        	}
        }
        
       private void drawHeart(Canvas canvas) {
    	   for (HeartLife life : lifeList) {
    		   life.DrawIt(canvas, screenWidth, screenHeight);
    	   }
       }
       
       private void drawScore(Canvas canvas) {
    		  score.Draw_score(canvas, screenWidth, screenHeight);
       }
        
        /**
         * Draws to the provided Canvas.
         */
        private void doDraw(Canvas canvas) {
        	
            // Draw the background color. Operations on the Canvas accumulate
            // so this is like clearing the screen. In a real game you can 
        	// put in a background image of course
        	canvas.drawColor(Color.BLACK);
        	
        	//Draw fps center screen
        	//canvas.drawText(fps + " fps", getWidth() / 2, getHeight() / 2, textPaint);

        	// Creation de l'illustration de fond
        	drawBackground(canvas);
        	
        	// Creation du chemin a empreinter
        	//drawPath(canvas);
        	
        	//creation de la chauve souris
        	drawBat(canvas);
        	
        	drawButtons(canvas);
        	
        	drawHeart(canvas);
        	
        	drawScore(canvas);
        	
        	canvas.restore();            
        }
        
        /**
         * So we can stop/pauze the game loop
         */
        public void setRunning(boolean b) {
            mRun = b;
        }      
        
       
      
    }

    /** The thread that actually draws the animation */
	
	
	private AnimationThread thread;
    private GestureDetector gestures;
    private float backgroundMoveX = 0;
    private float backgroundMoveY = 0;
    //private ArrayList<String> pathList;
    
    private ArrayList<ColorButton> buttonList;
    private List<Target>  targetList;
    private List<HeartLife>  lifeList;
    private List<MotionEvent>  list_singletap;;
    private Points score;
    
    private int target_id_focus = -1;
    private int target_id = 0;
    
    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        list_singletap = new ArrayList<MotionEvent>();
        myContext = context;
        score = new Points(myContext);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
       // initMapFile(context);
        
        gestures = new GestureDetector(context,  
                new GestureListener(this));
        
        // create thread only; it's started in surfaceCreated()
        thread = new AnimationThread(holder);

    }
   /* 
    private void initMapFile(Context context){
    	String filePath = "mapTest.map";
    	pathList = new ArrayList<String>();
    	
    	Scanner scanner;
		try {
			scanner = new Scanner(context.getResources().openRawResource(R.raw.map01));
			// On boucle sur chaque champ detecté
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				pathList.add(line);
				//Log.v("TEST", line);
				//System.out.println(line);
				//faites ici votre traitement
			}
			
			scanner.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("ERREUR", e.toString());
		}

    }
    */
    public boolean onTouchEvent(MotionEvent event) {  
        return gestures.onTouchEvent(event);  
    }  
    
   
    
    /**
     * Obligatory method that belong to the:implements SurfaceHolder.Callback
     */
    
    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    private class GestureListener implements GestureDetector.OnGestureListener,  
    GestureDetector.OnDoubleTapListener {  
    	AnimationView view;  
    	
    	public GestureListener(AnimationView view) {  
    		this.view = view;  
    	}
    	public boolean onDoubleTap(MotionEvent arg0) {
    		// TODO Auto-generated method stub
    		return false;
    	}
    	public boolean onDoubleTapEvent(MotionEvent arg0) {
    		// TODO Auto-generated method stub
    		return false;
    	}
    	public boolean onSingleTapConfirmed(MotionEvent arg0) {
    		// TODO Auto-generated method stub
    		return false;
    	}
    	public boolean onDown(MotionEvent arg0) {
    		Log.v("TOUCH", "onDown");  
    	    return true;  
    	}
    	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
    			float arg3) {
    		// TODO Auto-generated method stub
    		return false;
    	}
    	public void onLongPress(MotionEvent arg0) {
    		// TODO Auto-generated method stub
    		
    	}
    	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float distanceX,
    			float distanceY) {
    		Log.v("TOUCH", "onScroll. X=" + distanceX + " Y=" + distanceY);  
    		  
    	    view.onMove(distanceX, distanceY);  
    	    return true;  
    	}
    	public void onShowPress(MotionEvent arg0) {
    		// TODO Auto-generated method stub
    		
    	}
    	public boolean onSingleTapUp(MotionEvent event) {
    		// TODO Auto-generated method stub
    		Log.v("TAP", "tap");
    		list_singletap.add(event);
    		
    		/*
    		int x = (int)event.getX();
    	    int y = (int)event.getY();
    		int colorTouch = -1;
    	    boolean target_destroy = false;
    		
    	    Log.v("TOUCH", "x="+x+" y="+y);
    	    
    	    
    	    for (ColorButton button : buttonList) {
    	    	if(button.VerifTouchPosition(x, y) == true)
    	    		colorTouch = button.getColor();
    	    }
    	    
    	   
    	    if (colorTouch != -1) {
    	    	synchronized (targetList) {
					
		    	    for (Iterator<Target> iter = targetList.iterator(); iter.hasNext();) {
		    	    	Target target = iter.next();
		    	    	
		    	    	//ca veut dire que l'on a deja une cible
		    	    	if (target_id_focus != -1) {
		    	    		Log.v("LOG", "id focus="+target_id_focus);
		    	    		if (target.getId() == target_id_focus) {
		    	    			if (target.verifShot(colorTouch) == true) {
		    	    				if (target.getLife() == 0) {
		    	    					//iter.remove();'
		    	    					target_id_focus = -1;
		    	    					target_destroy = true;
		    	    					//target.setOnFire(false);
		    	    				}
		    	    				break;
		    	    			}
		    	    		}
		    	    	}
		    	    	//on a pas encore de cible
		    	    	else {
		    	    		if (target.verifShot(colorTouch) == true){
		    	    			target_id_focus = target.getId();
		    	    			//target.setOnFire(true);
		    	    			break;
		    	    		}
		    	    	}
		    	    }
    	    	}
    	    }
    	  //on a pas toucher un bouton couleur alors peut etre une cible pour la locker
	 		else {
	 			for (Target target : targetList) {
	 				if (target.isTouch(x, y) == true) {
	 					//unselectTarget(target_id_focus);
	 					target_id_focus = target.getId();
	 					//target.setOnFire(true);
	 					break;
	 				}
	 			}
	 		}
    	  */
    	  
    	  
    	  
    	  /*  
    	    if (target_id_focus != -1) {
    	    	targetList.add(new Bomb(myContext, target_id));
	    		target_id++;
	    		target_destroy = false;
    	    }
    	    */
    		return true;
    	}
    	/*
    	public void unselectTarget(int target_id_to_del) {
	 		for (Target target : targetList) {
 				if (target.getId() == target_id_to_del) {
 					target.setOnFire(false);
 					break;
 				}
 			}
	 	}
	 	*/
    }

	 public void onMove(float dx, float dy) {  
           // translate.postTranslate(dx, dy);
		 	backgroundMoveX = dx;
		 	backgroundMoveY = dy;

		 	
		 	
            invalidate();  
     }  
	 
	 public void VerifSimpletap() {
			
		 synchronized (list_singletap) {
     		for (Iterator<MotionEvent> itertap = list_singletap.iterator(); itertap.hasNext();) {
	    	    	MotionEvent event = (MotionEvent) itertap.next();
	    	    	
	    	    	int x = (int)event.getX();
		    	    int y = (int)event.getY();
		    	    
		    	    
		    		int colorTouch = -1;
		    	    boolean target_destroy = false;
		    		
		    	    Log.v("TOUCH", "x="+x+" y="+y);
		    	    
		    	    
		    	    for (ColorButton button : buttonList) {
		    	    	if(button.VerifTouchPosition(x, y) == true)
		    	    		colorTouch = button.getColor();
		    	    }
		    	    
		    	   
		    	    if (colorTouch != -1) {
		    	    	synchronized (targetList) {
							
				    	    for (Iterator<Target> iter = targetList.iterator(); iter.hasNext();) {
				    	    	Target target = iter.next();
				    	    	
				    	    	//ca veut dire que l'on a deja une cible
				    	    	if (target_id_focus != -1) {
				    	    		Log.v("LOG", "id focus="+target_id_focus);
				    	    		if (target.getId() == target_id_focus) {
				    	    			if (target.verifShot(colorTouch) == true) {
				    	    				if (target.getLife() == 0) {
				    	    					//iter.remove();'
				    	    					target_id_focus = -1;
				    	    					target_destroy = true;
				    	    					//target.setOnFire(false);
				    	    				}
				    	    				break;
				    	    			}
				    	    		}
				    	    	}
				    	    	//on a pas encore de cible
				    	    	else {
				    	    		if (target.verifShot(colorTouch) == true){
				    	    			target_id_focus = target.getId();
				    	    			//target.setOnFire(true);
				    	    			break;
				    	    		}
				    	    	}
				    	    }
		    	    	}
		    	    }
		    	  //on a pas toucher un bouton couleur alors peut etre une cible pour la locker
			 		else {
			 			for (Target target : targetList) {
			 				if (target.isTouch(x, y) == true) {
			 					//unselectTarget(target_id_focus);
			 					target_id_focus = target.getId();
			 					//target.setOnFire(true);
			 					break;
			 				}
			 			}
			 		}
		    	    itertap.remove();	
     		}
		 }
	 }
    
}
