package com.example.ray.meiliumaptest;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SSJK_MLJK_MLTActivity extends AppCompatActivity {
    List<String> paths=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssjk_mljk_mlt);

        paths.add("sis2.gif");
        paths.add("sis/transfer/lu_g_1_l.gif");
        paths.add("sis/transfer/h1_t6_rsm_r.gif");
        paths.add("sis/transfer/c10_bottom.gif");
        paths.add("sis/transfer/lu_g_6_r.gif");
        paths.add("sis/transfer/c10b_top_r.gif");
        GifSurfaceView gsf = (GifSurfaceView) findViewById(R.id.gsf);
        gsf.setPaths(paths);
        gsf.setZoom(0.5f);
        //改变Surface在MyWindow中的位置
        gsf.setZOrderOnTop(true);
        //把背景透明化
        gsf.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

}
