package com.xmahasiswa.bottulis;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;


class identitas_siswa {
    private String namaSiswa;
    private String kelas;
    private String mata_pelajaran;
    private Typeface font;
    TextView nama_siswa , view_Kelas , view_matapelajaran;
    public identitas_siswa(String namaSiswa , String kelas, String mata_pelajaran){
        this.namaSiswa = namaSiswa;
        this.kelas = kelas;
        this.mata_pelajaran = mata_pelajaran;
    }

    public identitas_siswa(String namaSiswa, String kelas, String mata_pelajaran, TextView nama_siswa, TextView view_Kelas, TextView view_matapelajaran) {
        this.namaSiswa = namaSiswa;
        this.kelas = kelas;
        this.mata_pelajaran = mata_pelajaran;
        this.nama_siswa = nama_siswa;
        this.view_Kelas = view_Kelas;
        this.view_matapelajaran = view_matapelajaran;
    }

    public void settIdentitas(){
        nama_siswa.setText("Nama = " + namaSiswa.toString());

        view_Kelas.setText("Kelas = " + kelas);
        view_matapelajaran.setText("Mata Pelajaran = " + mata_pelajaran);
    }
    public void setFont(Typeface tf){
        nama_siswa.setTypeface(font);
        view_Kelas.setTypeface(font);
        view_matapelajaran.setTypeface(font);
    }
}


class ImagePaperListclass{
    int image;
    String namePapper;
    public ImagePaperListclass(int image,String namePapper){
        this.image = image;
        this.namePapper = namePapper;
    }

}

    public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    ArrayList<EditText> editList = new ArrayList<>();
        ArrayList<ViewGroup.LayoutParams> myLP = new ArrayList<>();
        float DX , DY;
    float text_sizeList = 10.0f;
    float line_spacingList = 1.0f;
    int textSize = (int) 15.0f;

    int ImageList[];

        public Bitmap drawMultilineTextToBitmap(Context gContext,
                                                int gResId,
                                                String gText) {

            // prepare canvas
            Resources resources = gContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

              Canvas canvas = new Canvas(bitmap);
            Toast.makeText(getApplicationContext(),"Draw Succes", )
            // new antialiased Paint
            TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(61, 61, 61));
            // text size in pixels
            paint.setTextSize((int) (14 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

            // set text width to canvas width minus 16dp padding
            int textWidth = canvas.getWidth() - (int) (16 * scale);

            // init StaticLayout for text
            StaticLayout textLayout = new StaticLayout(
                    gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

            // get height of multiline text
            int textHeight = textLayout.getHeight();

            // get position of text's top left corner
            float x = (bitmap.getWidth() - textWidth)/2;
            float y = (bitmap.getHeight() - textHeight)/2;

            // draw text to the Canvas center
            canvas.save();
            canvas.translate(x, y);
            textLayout.draw(canvas);
            canvas.restore();

            return bitmap;
        }


    ArrayList<EditText> removeEditText(RelativeLayout s , ArrayList<EditText> editTextList){
        if(editList.size() != 0){
            editList.get(editList.size() - 1).setVisibility(View.INVISIBLE);
            editList.get(editList.size() - 1);
            editList.remove(editList.size() - 1);
        }
        return editList;
    }
    int total = 0;
    ArrayList<EditText> generateEditText (RelativeLayout s , int getDefaultWidth , int getDefaultHeight){
        EditText sx = new EditText(getApplicationContext());
        sx.setId(View.generateViewId());
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.child_writing);
        sx.setTypeface(typeFace);
        total++;
        sx.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
        sx.setLineSpacing(line_spacingList,1.5f);
        sx.setText("Element Ke " + total);
        sx.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
        sx.setTextColor(getResources().getColor(R.color.black));
        sx.setPaintFlags(0);
        sx.setMaxLines(100);
        sx.setHorizontallyScrolling(false);
        sx.setSingleLine(false);
        sx.setGravity(Gravity.TOP|Gravity.LEFT);
        sx.setEnabled(true);
        sx.setX(430.0F);
        sx.setY(300.0F);;

        sx.setTop(20);
        sx.setBottom(30);

        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.MATCH_PARENT);

        sx.setLayoutParams(rl);
        editList.add(sx);
        s.addView(sx);
        return editList;
    }

    class positionEditText{
        float top , bottom , left, right;

        public void setTop(float top) {
            this.top = top;
        }

        public void setBottom(float bottom) {
            this.bottom = bottom;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public void setRight(float right) {
            this.right = right;
        }
    }
    public void getPositionOfEditText(ArrayList<EditText> o){
        ArrayList<Float> postFloatX = new ArrayList<>();
        ArrayList<positionEditText> listPostion = new ArrayList<>();
        for(int c = 0; c< o.size();c++){
            listPostion.get(c).setTop(o.get(c).getTop());
            listPostion.get(c).setBottom(o.get(c).getBottom());
            listPostion.get(c).setRight(o.get(c).getRight());
            listPostion.get(c).setLeft(o.get(c).getLeft());
        }
    }
    ArrayList<Float> getPositionOfX = new ArrayList<>();
        ArrayList<Float> getPositionOfY = new ArrayList<>();

        public void enableMoveAllElement(ArrayList<EditText> s){
        int n = s.size();

        Toast.makeText(getApplicationContext(),"N Enable Move Size: " + n , Toast.LENGTH_SHORT).show();
        for(int d = 0; d < n; d++){
            s.get(d).setTextColor(getResources().getColor(R.color.black));
            s.get(d).setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            s.get(d).setHorizontallyScrolling(false);
            s.get(d).setSingleLine(false);
            s.get(d).setGravity(Gravity.TOP|Gravity.LEFT);
            s.get(d).getLayoutParams();
            s.get(d).setEnabled(true);
            s.get(d).setBackground(null);
            s.get(d).setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            s.get(d).setVerticalScrollBarEnabled(true);

            int finalD = d;
            s.get(d).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:

                            DX = v.getX() - event.getRawX();
                            DY= v.getY() - event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:

                            Toast.makeText(getApplicationContext(),"Action Move" + finalD, Toast.LENGTH_SHORT).show();
                            v.animate().x(event.getRawX() + DX).y(event.getRawY() + DY).setDuration(0).start();
                            break;
                        case MotionEvent.ACTION_BUTTON_PRESS:
                           Toast.makeText(getApplicationContext(),"Text Telah dipilih" + finalD,Toast.LENGTH_SHORT).show();

                        default:
                            return false;
                    }
                    return true;
                }
            });
        }
    }
    public void disableMoveAllElement(ArrayList<EditText> s){
        int n = s.size();

        Toast.makeText(getApplicationContext(),"N disableMove Size: " + n , Toast.LENGTH_SHORT).show();
        for(int d = 0; d < n; d++){
            /*
            s.get(d).setOnTouchListener(null);
            s.get(d).setFocusable(true);
            s.get(d).isFocusableInTouchMode();
           */
            s.get(d).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            s.get(d).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            s.get(d).setMaxLines(100);
            s.get(d).setHorizontallyScrolling(false);
            s.get(d).setHorizontalScrollBarEnabled(false);

            s.get(d).setSingleLine(false);
            s.get(d).setGravity(Gravity.TOP|Gravity.LEFT);
            s.get(d).setEnabled(true);
            s.get(d).setOnTouchListener(null);
        }
    }

    EditText text_field;
    ImageView editPosition , input_kalimat,add_text,delete_text,customFont;
    TextView nama_siswa, view_Kelas, view_matapelajaran;
    //new declare
    ImageView simpan_tulisan , ganti_kertas,ukuran_font , ketinggian_teks,take_kertas;
    ImageView share_instant_ic;
    RelativeLayout layout_main;
    ConstraintLayout LayoutDialogPapper;
    LinearLayout rightBarLayout;
    ImageView hide_Icon;
    ConstraintLayout dialogSHARE;
    ImageView button_KembaliSHARE;
    LinearLayout identitas_layout;
    //ImageView Papper Main
        ImageView imgMain;
    //listener Adapter
    AdapterPapper.adapterListener listener;
    final static float move = 200;
    float ratio = 1.0f;
    int baseDist;
    float baseRatio;
    float dX ,dY;
        private static int RESULT_LOAD_IMG = 1;
        String imgDecodableString;

        float factorGesture = 1.0f;
        ScaleGestureDetector SGD;
    LinearLayout Font_Size_Layout;
    ImageView perbesarView , perkecilView;
    RecyclerView Papper_Recycle;
ActivityResultLauncher<Intent> activityresultLauncher;
    public void enablePapperRecycle(ArrayList<ImagePaperListclass> list_image){


        listener = new AdapterPapper.adapterListener() {
            @Override
            public void onClick(View v, int position) {
                    imgMain.setImageResource(list_image.get(position).image);

            }
        };
        AdapterPapper pAdapterPaper = new AdapterPapper(list_image,listener);
        Papper_Recycle = findViewById(R.id.rec_ListPapper);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        Papper_Recycle.setLayoutManager(horizontalLayout);
        Papper_Recycle.setAdapter(pAdapterPaper);
    }
    public void showDialogPapper(ArrayList<ImagePaperListclass> listImg){
        LayoutDialogPapper = findViewById(R.id.Layout_dialogPapper);
        ganti_kertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   LayoutDialogPapper.setVisibility(View.INVISIBLE);
               loadImagefromGallery();
            }
        });
    }
    public ArrayList<ImagePaperListclass> addImage(int listImg[]){
        ArrayList<ImagePaperListclass> objPapper = new ArrayList<>();
        for(int t = 0; t< listImg.length; t++){
            objPapper.add(new ImagePaperListclass(listImg[t],"Paper-" + t));
        }
        return objPapper;
    }
    BitmapDrawable bitDrawable;

    public void hideNavigationBar(){
        View getDecorview = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        getDecorview.setSystemUiVisibility(uiOptions);
    }
    public void hideBar(){
        rightBarLayout = findViewById(R.id.right_bar);
        hide_Icon = findViewById(R.id.id_hidebox);
        hide_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                if(rightBarLayout.getVisibility() == View.VISIBLE){
                    rightBarLayout.setVisibility(View.INVISIBLE);
                    hide_Icon.setRotation(180F);
                } else if(rightBarLayout.getVisibility() == View.INVISIBLE){
                    rightBarLayout.setVisibility(View.VISIBLE);
                    hide_Icon.setRotation(0F);
                }
            }
        });
    }
    public void shareFunction(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        layout_main.setDrawingCacheEnabled(true);
        layout_main.buildDrawingCache();
        BitmapDrawable bitdrawable = new BitmapDrawable(layout_main.getDrawingCache());

        Bitmap bitmap = bitdrawable.getBitmap();
        File path = Environment.getExternalStorageDirectory();
        File file = new File(getExternalCacheDir() + "/" + getResources().getString(R.string.app_name)+".png");
        Intent shareIntent;
        File saveFile = new File(file,"export" + ".png");
        try {
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);

            ostream.flush();;

            ostream.close();
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            layout_main.invalidate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            layout_main.setDrawingCacheEnabled(false);
        }
        startActivity(Intent.createChooser(shareIntent,"share image"));
    }


    private String currentPhotoPath;
    SharedPreferences sharedOfMargin;
    SharedPreferences.Editor editOfMargin;
    public void getMargin() {
     try {

         for (int t = 0; t < editList.size(); t++) {
             myLP.add(editList.get(t).getLayoutParams());
             Toast.makeText(getApplicationContext(), "X" + editList.get(t).getX(), Toast.LENGTH_LONG).show();
         }
         Toast.makeText(getApplicationContext(), " " + myLP, Toast.LENGTH_LONG).show();
       } catch (NullPointerException e){
         e.printStackTrace();
     }
     }
    public void saveMargin(){
        getMargin();
        Type tp = new TypeToken<ArrayList<RelativeLayout.LayoutParams>>(){}.getType();
        Gson gson = new Gson();
        String toJson = gson.toJson(myLP,tp);
        sharedOfMargin = getSharedPreferences("key_margin",MODE_PRIVATE);
        editOfMargin = sharedOfMargin.edit();
        Toast.makeText(getApplicationContext(),toJson.toString(),Toast.LENGTH_LONG).show();
        editOfMargin.putString("key_margin_value",toJson);
        editOfMargin.apply();
    }
    public void getData(){
        try{
        Type tp = new TypeToken<ArrayList<RelativeLayout.LayoutParams>>(){}.getType();
        Gson gson_Get = new Gson();
        SharedPreferences getSharedOfMargin = getSharedPreferences("key_margin",MODE_PRIVATE);
        String getDataFromShared = getSharedOfMargin.getString("key_margin_value","");
        Toast.makeText(getApplicationContext(),"DATA = " + getDataFromShared ,Toast.LENGTH_LONG).show();
        ArrayList<RelativeLayout.LayoutParams> getLP = gson_Get.fromJson(getDataFromShared,tp);
        int count = 0;
        for(int t = 0; t<getLP.size();t++){
            EditText sx = new EditText(getApplicationContext());
            sx.setId(View.generateViewId());
            Typeface typeFace = ResourcesCompat.getFont(this, R.font.child_writing);
            sx.setTypeface(typeFace);
            sx.setText("Hello" + t);
            count++;
            sx.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
            sx.setLineSpacing(line_spacingList,1.5f);
            sx.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
            sx.setTextColor(getResources().getColor(R.color.black));
            sx.setPaintFlags(0);
            sx.setMaxLines(100);
            sx.setHorizontallyScrolling(false);
            sx.setSingleLine(false);
            sx.setGravity(Gravity.TOP|Gravity.LEFT);
            sx.setEnabled(true);
            sx.setLayoutParams(getLP.get(t));
            editList.add(sx);
            layout_main.addView(sx);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void takePhoto(){

        String fileName = "Photo";
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        take_kertas = findViewById(R.id.kamera_view);
        take_kertas.setOnClickListener(new View.OnClickListener() {

            ContentValues values;
            Uri uri;
            @Override
            public void onClick(View v) {
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {

                    File ImageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = ImageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(MainActivity.this,"com.xmahasiswa.bottulis.fileprovider",ImageFile);
                    Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        activityresultLauncher.launch(intentPhoto);
                } catch (IOException e){
                    e.printStackTrace();
                }

                 }
        });

        activityresultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK ) {

                        Bitmap captureImage = BitmapFactory.decodeFile(currentPhotoPath);
                        imgMain.setImageBitmap(decodeImage(currentPhotoPath));
                }
            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            try {
                // When an Image is picked
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imgView = (ImageView) findViewById(R.id.paperView);
                    // Set the Image in ImageView after decoding the String
                    Bitmap bitmapCompresed = decodeImage(imgDecodableString.toString());
                    imgView.setImageBitmap(bitmapCompresed);

                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }
        public void loadImagefromGallery() {
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
        public Bitmap decodeImage(String uri) {
            try {
                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(uri
                        ,o);
                // The new size we want to scale to
                final int REQUIRED_SIZE = 712; // you are free to modify size as your requirement
                // Find the correct scale value. It should be the power of 2.
                int scale = 1;
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                // Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                return BitmapFactory.decodeFile(uri, o2);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

    //        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == 100) {
//                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
//                imgMain.setImageBitmap(captureImage);
//            }
//        }


        public void showDialogShare(){
            dialogSHARE = findViewById(R.id.dialog_layoutShare);
            button_KembaliSHARE = findViewById(R.id.btn_kirim_guru);
            dialogSHARE.setVisibility(View.VISIBLE);
            ObjectAnimator obj = ObjectAnimator.ofFloat(dialogSHARE,dialogSHARE.TRANSLATION_Y,0);
            obj.setDuration(1300L);
            obj.setInterpolator(new BounceInterpolator());
            obj.start();
            button_KembaliSHARE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator obj = ObjectAnimator.ofFloat(dialogSHARE,dialogSHARE.TRANSLATION_Y,1500);
                    obj.setDuration(1300L);
                    obj.setInterpolator(new AnticipateOvershootInterpolator());
                    obj.start();
                }
            });
        }

        TextView tv_gantikertas , tv_ukuranfont , tv_ketinggian_teks , tv_shareinstant , tv_rotatetext;
        public void barright(ArrayList<ImagePaperListclass> listArray ){

            //textview
            tv_gantikertas = findViewById(R.id.tv_gantiKertas);
            tv_ukuranfont = findViewById(R.id.tv_ukuranTeks);
            tv_ketinggian_teks = findViewById(R.id.tv_ketinggianTeks);
            tv_shareinstant = findViewById(R.id.tv_shareInstant);
            tv_rotatetext =findViewById(R.id.tv_rotasiText);


    simpan_tulisan = findViewById(R.id.simpan_text);
    ganti_kertas = findViewById(R.id.ganti_kertas);
    ukuran_font = findViewById(R.id.id_ukuranfont);
    Font_Size_Layout = findViewById(R.id.Resizer_Layout);
    perbesarView = findViewById(R.id.perbesar_id);
    perkecilView = findViewById(R.id.perkecil_id);
    ketinggian_teks = findViewById(R.id.id_ketinggian_teks);
    share_instant_ic = findViewById(R.id.sharecepat);
            rotateTextView = findViewById(R.id.id_rotatetext);
            rotateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if(Font_Size_Layout.getVisibility() == View.VISIBLE) {
                      Font_Size_Layout.setVisibility(View.INVISIBLE);
                      rotateTextView.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                      tv_rotatetext.setTextColor(getResources().getColor(R.color.white));
                  }else if(Font_Size_Layout.getVisibility() == View.INVISIBLE) {
                      Font_Size_Layout.setVisibility(View.VISIBLE);
                      rotateTextView.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.black));
                      tv_rotatetext.setTextColor(getResources().getColor(R.color.black));
                      functionRotateTeks();
                  }
                }
            });

    share_instant_ic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shareFunction();
            showDialogShare();
        }
    });
    takePhoto();
    int n = 0;
    simpan_tulisan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layout_main.setDrawingCacheEnabled(true);
            layout_main.buildDrawingCache();
            Bitmap bitmap = layout_main.getDrawingCache();
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path.getAbsolutePath() + "/bottulis/");
            file.mkdir();
            File saveFile = new File(file,"bottulisku" + System.currentTimeMillis() + ".png");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream ostream = new FileOutputStream(saveFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                ostream.close();
                layout_main.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                layout_main.setDrawingCacheEnabled(false);
            }
            Toast.makeText(v.getContext(),"Gambar Tersimpan ",Toast.LENGTH_SHORT).show();
        }
    });


    ukuran_font.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Font_Size_Layout.getVisibility() == View.VISIBLE) {
                Font_Size_Layout.setVisibility(View.INVISIBLE);
                ukuran_font.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                tv_ukuranfont.setTextColor(getResources().getColor(R.color.white));
            }else if(Font_Size_Layout.getVisibility() == View.INVISIBLE){
                Font_Size_Layout.setVisibility(View.VISIBLE);
                ukuran_font.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.black));
                tv_ukuranfont.setTextColor(getResources().getColor(R.color.black));
                functionResizeText();
            }
        }
    });
    ketinggian_teks.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Font_Size_Layout.getVisibility() == View.VISIBLE) {
                Font_Size_Layout.setVisibility(View.INVISIBLE);
                ketinggian_teks.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                tv_ketinggian_teks.setTextColor(getResources().getColor(R.color.white));
            }else if(Font_Size_Layout.getVisibility() == View.INVISIBLE){
                Font_Size_Layout.setVisibility(View.VISIBLE);
                ketinggian_teks.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.black));
                tv_ketinggian_teks.setTextColor(getResources().getColor(R.color.black));
                functionResizeHeightLine();
            }
        }
    });
    showDialogPapper(listArray);
}

public void functionResizeHeightLine(){
    perbesarView = findViewById(R.id.perbesar_id);
    perkecilView = findViewById(R.id.perkecil_id);
    if(Font_Size_Layout.getVisibility() == View.VISIBLE){
        perbesarView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                line_spacingList += 1.0f;
                increaseHeightTextALL(line_spacingList);
                Toast.makeText(v.getContext(),"Ketinggian bertambah " ,Toast.LENGTH_SHORT).show();
            }
        });
        perkecilView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                line_spacingList -= 1.0f;
                decreaseHeightTextALL(line_spacingList);
            }
        });
    }


}

float rotationText = 1.0f;
public void functionRotateTeks(){
    perbesarView = findViewById(R.id.perbesar_id);
    perkecilView = findViewById(R.id.perkecil_id);
        perbesarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationText += 1.0f;
                for(int t = 0; t< editList.size(); t++){
                    editList.get(t).setRotation(rotationText);


                    identity_siswa.nama_siswa.setRotation(rotationText);
                    identity_siswa.view_matapelajaran.setRotation(rotationText);
                    identity_siswa.view_Kelas.setRotation(rotationText);

                }
                Toast.makeText(v.getContext(),"Posisi Rotasi +  " + rotationText,Toast.LENGTH_SHORT).show();
            }
        });
        perkecilView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationText -= 1.0f;
                for(int t = 0; t< editList.size(); t++){
                    editList.get(t).setRotation(rotationText);
                   identity_siswa.nama_siswa.setRotation(rotationText);
                    identity_siswa.view_matapelajaran.setRotation(rotationText);
                    identity_siswa.view_Kelas.setRotation(rotationText);
                }
                Toast.makeText(v.getContext(),"Posisi Rotasi -  " + rotationText,Toast.LENGTH_SHORT).show();
            }
        });
}
public void functionResizeText(){
    if(Font_Size_Layout.getVisibility() == View.VISIBLE){
        perbesarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_sizeList += 1.0f;
                increaseSizeTextALL(editList);
                Toast.makeText(v.getContext(),"Text Diperbesar ",Toast.LENGTH_SHORT).show();
            }
        });
        perkecilView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_sizeList -= 1.0f;
                decreaseSizeTextALL(editList,text_sizeList);
            }
        });
    }
}

@RequiresApi(api = Build.VERSION_CODES.P)
public void decreaseHeightTextALL(float text_height){
        for(int t = 0; t< editList.size();t++){
                editList.get(t).setLineSpacing(line_spacingList,0.5F);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        public void increaseHeightTextALL(float text_Height){
            for(int t = 0; t< editList.size();t++){
                editList.get(t).setLineSpacing(line_spacingList,0.5F);
            }
        }

public void decreaseSizeTextALL(ArrayList<EditText> Text,float text_sizeList){
    int nSize = editList.size();
    for(int d= 0; d< nSize; d++){
        editList.get(d).setTextSize(text_sizeList);
        identity_siswa.nama_siswa.setTextSize(text_sizeList);
        identity_siswa.view_matapelajaran.setTextSize(text_sizeList);
        identity_siswa.view_Kelas.setTextSize(text_sizeList);
    }
}
public void increaseSizeTextALL(ArrayList<EditText> objEditText) {
    int nSize = objEditText.size();
    for (int d = 0; d < nSize; d++) {
        objEditText.get(d).setTextSize(text_sizeList);
        identity_siswa.nama_siswa.setTextSize(text_sizeList);
        identity_siswa.view_matapelajaran.setTextSize(text_sizeList);
        identity_siswa.view_Kelas.setTextSize(text_sizeList);
    }
}
RecyclerView recFontList;
    AdapterFont adapterFont;
    AdapterFont.adapterFontListener listenerFont;

    ImageView rotateTextView;
    public void rotateFunction(){

    }
        public identitas_siswa identity_siswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavigationBar();

        Bundle extraBundle = getIntent().getExtras();
        ArrayList<String> data_siswa = new ArrayList<>();
        data_siswa = extraBundle.getStringArrayList("data_intent");
        nama_siswa = findViewById(R.id.tv_namasiswa);
        view_Kelas = findViewById(R.id.tv_kelas);
        view_matapelajaran = findViewById(R.id.tv_matapelajaran);
        text_field = findViewById(R.id.list_text); // teks test
        layout_main = findViewById(R.id.layout_paper); // kertas
        customFont = findViewById(R.id.id_customfont); // ganti font
        imgMain = findViewById(R.id.paperView);
        recFontList = findViewById(R.id.font_recycleview);
        Toast.makeText(getApplicationContext(), "Selamat Datang\n" + data_siswa.get(0) + "\n" + data_siswa.get(1) + "\n" + data_siswa.get(2), Toast.LENGTH_SHORT).show();
         identity_siswa = new identitas_siswa(data_siswa.get(0), data_siswa.get(1), data_siswa.get(2), nama_siswa, view_Kelas, view_matapelajaran);
        identity_siswa.settIdentitas();
        text_field.setTextSize(ratio+15);
        editPosition = findViewById(R.id.oke_button);
        input_kalimat = findViewById(R.id.id_inputkalimat);
        add_text= findViewById(R.id.add_text);
        getMargin();
        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Add Text" , Toast.LENGTH_SHORT).show();;
                generateEditText(layout_main,input_kalimat.getWidth(),input_kalimat.getHeight());
                saveMargin();
            }
        });
        delete_text = findViewById(R.id.delete_text);
        delete_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Delete Text" , Toast.LENGTH_SHORT).show();;
                removeEditText(layout_main,editList);
                saveMargin();
            }
        });
        input_kalimat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_field.setEnabled(false);
                disableMoveAllElement(editList);
            }
        });
        editPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customFont.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                if(editPosition.getColorFilter().equals(ContextCompat.getColor(getApplicationContext(),R.color.white))){

                }
                text_field.setEnabled(true);
                enableMoveAllElement(editList);
            }
        });
        text_field.setVisibility(View.INVISIBLE);
        ImageList = new int[]{R.drawable.paper1_nobg, R.drawable.paper_6, R.drawable.paper_7
        , R.drawable.paper_8};
        ArrayList<Integer> fontList = new ArrayList<>();
        fontList.add(R.font.child_writing);
        fontList.add(R.font.fonts_1);
        fontList.add(R.font.fonts_2);
        fontList.add(R.font.fonts_3);
        fontList.add(R.font.fonts_4);
        fontList.add(R.font.fonts_5);
        fontList.add(R.font.fonts_6);
        fontList.add(R.font.fonts_7);
        fontList.add(R.font.fonts_8);
        fontList.add(R.font.fonts_9);
        fontList.add(R.font.fonts_10);
        fontList.add(R.font.fonts_11);
        fontList.add(R.font.fonts_12);
        fontList.add(R.font.fonts_13);
        fontList.add(R.font.fonts_14);
        fontList.add(R.font.fonts_15);
        fontList.add(R.font.fonts_16);
        fontList.add(R.font.fonts_17);
        fontList.add(R.font.fonts_18);
        fontList.add(R.font.ackipreschool);
        fontList.add(R.font.adamhandwritingtest);
        fontList.add(R.font.agrementsignature);
        fontList.add(R.font.ahthemonsteriscoming);
        fontList.add(R.font.akeylah__s_handwriting);
        fontList.add(R.font.angelinahandwriting);
        fontList.add(R.font.firstgradelolo);
        fontList.add(R.font.homemadeappleregular);
        fontList.add(R.font.istillbelieve);
        fontList.add(R.font.kbsothinteresting);
        fontList.add(R.font.kindergarden);
        fontList.add(R.font.kssothinterestingbold);
        fontList.add(R.font.linny);
        fontList.add(R.font.ltcoloredpenci);
        fontList.add(R.font.my_handwriting);
        fontList.add(R.font.my_unprofessional_handwriting);
        fontList.add(R.font.naomiahandregular);
        fontList.add(R.font.ninifontcaps);
        fontList.add(R.font.phitradesignhandwrittenthin);
        fontList.add(R.font.rcmydreamfont);
        fontList.add(R.font.rightyuseslefthand);
        fontList.add(R.font.ryleshand);
        fontList.add(R.font.saras_font);
        fontList.add(R.font.sourcreamandonionregular);
        fontList.add(R.font.tafelschrift);
        fontList.add(R.font.tommysfirstalphabetregular);
        listenerFont = new AdapterFont.adapterFontListener() {
            @Override
            public void onClick(View v, int position) {
                Typeface tf = ResourcesCompat.getFont(getApplicationContext(),fontList.get(position));
                for(int i = 0; i< editList.size(); i++){
                    editList.get(i).setTypeface(tf);
                    identity_siswa.nama_siswa.setTypeface(tf);
                    identity_siswa.view_matapelajaran.setTypeface(tf);
                    identity_siswa.view_Kelas.setTypeface(tf);
                }

            }
        };
        adapterFont = new AdapterFont(fontList,getApplicationContext(),listenerFont);
        recFontList.setAdapter(adapterFont);
        LinearLayoutManager horizontalLayoutFont = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recFontList.setLayoutManager(horizontalLayoutFont);
        customFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(recFontList.getVisibility() == View.VISIBLE){
                    recFontList.setVisibility(View.INVISIBLE);
                } else if(recFontList.getVisibility() == View.INVISIBLE){
                    recFontList.setVisibility(View.VISIBLE);
                }
            }
        });

        customFont.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        customFont.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.black));
                        break;
                    case MotionEvent.ACTION_UP:
                        customFont.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.white));
                        break;
                }
                return false;
            }
        });
        ArrayList<ImagePaperListclass> arrayListImage = addImage(ImageList);
        barright(arrayListImage);

        hideBar();
        identitas_layout = findViewById(R.id.identitas_kamu);
        identitas_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        DX = v.getX() - event.getRawX();
                        DY= v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        Toast.makeText(getApplicationContext(),"Action Move" , Toast.LENGTH_SHORT).show();
                        v.animate().x(event.getRawX() + DX).y(event.getRawY() + DY).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        }


        class scaleImages extends MainActivity{
        ImageView imgView;
        ScaleGestureDetector sgd;
        Context ctx;
        public  scaleImages(ImageView img_v, Context ctx){
            this.imgView = img_v;
            this.ctx = ctx;
        }

        public void enableZoomInOut(){
            sgd = new ScaleGestureDetector(this.ctx,new ListenerScale());
        }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                    return sgd.onTouchEvent(event);
            }
        private class ListenerScale extends ScaleGestureDetector.SimpleOnScaleGestureListener{
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                factorGesture *= sgd.getScaleFactor();
                imgView.setScaleX(factorGesture);
                imgView.setScaleY(factorGesture);
                factorGesture = Math.max(0.1f,Math.min(factorGesture,1.0f));
                return true;
            }
        }
    }
        class move_View implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    dX = v.getX() - event.getRawX();
                    dY= v.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.animate().x(event.getRawX() + dX).y(event.getRawY() + dY).setDuration(0).start();
                    break;
                default:
                    return false;
            }
            return  true;
        }
    }
    class resize_Text extends MainActivity implements View.OnTouchListener{
        EditText textList;
        public resize_Text(EditText textList){
            this.textList = textList;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getPointerCount() == 2) {
                int action = event.getAction();
                int mainAction = action & MotionEvent.ACTION_MASK;
                if (mainAction == MotionEvent.ACTION_POINTER_DOWN) {
                    baseDist = getDistance(event);
                    baseRatio = ratio;
                } else {
                    float scale = (getDistance(event) - baseDist) / move;
                    float factor = (float) Math.pow(2, scale);
                    ratio = Math.min(1024.0f, Math.max(0.1f, baseRatio * factor));
                    textList.setTextSize(ratio + 15);
                }
            }
            return  true;
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int mainAction = action & MotionEvent.ACTION_MASK;
            if (mainAction == MotionEvent.ACTION_POINTER_DOWN) {
                baseDist = getDistance(event);
                baseRatio = ratio;
            } else {
                float scale = (getDistance(event) - baseDist) / move;
                float factor = (float) Math.pow(2, scale);
                ratio = Math.min(1024.0f, Math.max(0.1f, baseRatio * factor));
                text_field.setTextSize(ratio + 15);
            }
        }
        return  true;
}

    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }
}