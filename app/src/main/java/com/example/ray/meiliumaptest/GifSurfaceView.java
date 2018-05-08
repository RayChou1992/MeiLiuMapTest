package com.example.ray.meiliumaptest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2018/1/31.
 */

public class GifSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    //gif图片路径
    private String path;
    private Movie movie;
    private Movie movie1;
    //执行动画
    private Handler handler;
    //放大倍数
    private float zoom;

    private List<String> paths;
    private List<Movie> movies;
    List<int[]> gifPositon = new ArrayList<>();
    Bitmap bm;
    Resources r;

    //构造函数
    public GifSurfaceView(Context context) {
        super(context);
        initParam();
    }

    public GifSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParam();
    }

    public GifSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParam();
    }


    public void setPaths(List<String> paths) {
        this.paths = paths;
        //各gif图片的位置
        gifPositon.add(new int[]{0, 0});
        gifPositon.add(new int[]{1453, 156});
        gifPositon.add(new int[]{1214, 678});
        gifPositon.add(new int[]{2036, 146});
        gifPositon.add(new int[]{1742, 160});
        gifPositon.add(new int[]{1421, 472});
//        gifPositon.add(new int[]{1238, 1558-(int)(500/fenbianlv)});
//        gifPositon.add(new int[]{290, 673+(int)(80*fenbianlv)});
//        gifPositon.add(new int[]{665, 343+(int)(280*fenbianlv)});
//        gifPositon.add(new int[]{317, 487+(int)(130*fenbianlv)});
//        gifPositon.add(new int[]{1805, 1457-(1000)});
//        gifPositon.add(new int[]{1188, 585});
//        gifPositon.add(new int[]{1220, 460});
    }

    //线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //获取画布(加锁)
            Canvas canvas = holder.lockCanvas();
            canvas.save();
            canvas.scale(zoom, zoom);    //x为水平方向的放大倍数，y为竖直方向的放大倍数。
            //绘制此gif的某一帧，并刷新本身
            for (int i = 0; i < movies.size(); i++) {

                movies.get(i).draw(canvas, gifPositon.get(i)[0], gifPositon.get(i)[1]);
                //逐帧绘制图片(图片数量5)
                // 1 2 3 4 5 6 7 8 9 101310
                // 1 2 3 4 0 1 2 3 4 0  循环
                int duration = movie.duration();
                if (duration == 0) {

                    movie.setTime((int) (System.currentTimeMillis() % 1));
                } else {
                    movie.setTime((int) (System.currentTimeMillis() % duration));

                }
            }
            canvas.restore();
            //结束锁定画图，并提交改变,画画完成(解锁)
            holder.unlockCanvasAndPost(canvas);

            handler.postDelayed(runnable, 50);   //50ms表示每50ms绘制一帧
        }
    };


    /**
     * 初始化参数
     */
    private void initParam() {
        holder = getHolder();
        holder.addCallback(this);
        handler = new Handler();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    /**
     * 计算视图宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (paths != null) {
            movies = new ArrayList<>();
            for (String path : paths) {
                try {
                    InputStream stream = getContext().getAssets().open(path);
                    movie = Movie.decodeStream(stream);
                    if (path.equals(paths.get(0))){
                        //获取gif的宽高
                        int width = movie.width();
                        int height = movie.height();
                        setMeasuredDimension((int) (width * zoom), (int) (height * zoom));
                    }
                    //执行gif动画
                    movies.add(movie);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            handler.post(runnable);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //停止gif动画
        handler.removeCallbacks(runnable);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }
}
