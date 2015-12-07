package com.example.owner.recognitioningmanaca;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

import com.ImageRecognition.callback.GPImageRecognitionEngineCallback;
import com.ImageRecognition.data.GPImageRecognitionEngineResult;
import com.ImageRecognition.exception.CameraEndFailedException;
import com.ImageRecognition.exception.CameraStartFailedException;
import com.ImageRecognition.manager.GPImageRecognitionEngine;
import com.ImageRecognition.manager.recognizer.GPFineRecognizer;



public  class MainActivity extends Activity implements GPImageRecognitionEngineCallback, OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);

        _engine = GPImageRecognitionEngine.getInstance();
        _engine.setPreviewView(surface);
        _engine.setProcessMethod(this);
        _engine.set        PreviewOrientation(GPImageRecognitionEngine.PreviewOrientation.PORTRAIT);

        GPFineRecognizer _recognizer = new GPFineRecognizer();
        _engine.setGPImageRecognizer(_recognizer);

        _recognizer.loadDatasetBinary(this, binBuffer, yamlBuffer);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (_engine != null) {
            try {
                _engine.Start();
            } catch (CameraStartFailedException e) {
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (_engine != null) {
            try {
                _engine.Stop();
            } catch (CameraEndFailedException e) {
            }
        }
    }

    @Override
    public void ImageRecognitionResult(GPImageRecognitionEngineResult result) {
        if (result.isRecognized) {
            try {
             _engine.Stop();
            } catch (CameraEndFailedException e) {
                e.printStackTrace();
            }

            showDialog(result);
        }
    }

    public void showDialog(GPImageRecognitionEngineResult result) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

