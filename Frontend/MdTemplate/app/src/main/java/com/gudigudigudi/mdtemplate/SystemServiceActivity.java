package com.gudigudigudi.mdtemplate;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class SystemServiceActivity extends AppCompatActivity {

    private static final String TAG = "SystemServiceActivity";

    public static final String FILE_PRIVIDER = "com.gudigudigudi.mdtemplate.fileprivider";
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 2;

    private Button btn_send_notification;

    private Button btn_take_photo;
    private Uri imageUri;
    private ImageView picture;

    private Button btn_choose_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_service);

        btn_send_notification = (Button) findViewById(R.id.send_notification);

        btn_send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SystemServiceActivity.this, NotificationToggledActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(SystemServiceActivity.this, 0, intent, 0);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification = new NotificationCompat.Builder(getApplicationContext(), null)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_arrow_back)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(new long[]{0, 1000, 1000, 1000})
                        .setLights(Color.GREEN, 1000, 1000)
                        .build();

                notificationManager.notify(1, notification);
            }
        });

        btn_take_photo = (Button) findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.picture);
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(SystemServiceActivity.this, FILE_PRIVIDER, outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }

                // start camera.
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            }
        });

        btn_choose_photo = (Button) findViewById(R.id.choose_from_album);
        btn_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SystemServiceActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SystemServiceActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CHOOSE_PHOTO);
                } else {
                    openAlbum();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
                                picture.getWidth(), picture.getWidth() * bitmap.getHeight() / bitmap.getWidth(), false));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_CODE_CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                Log.d(TAG, "Unknown requestCode.");
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(SystemServiceActivity.this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d(TAG, "Unknown request code.");
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_PHOTO);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            // Uri type is document.
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Uri type is content.
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // Uri type is file.
            imagePath = uri.getPath();
        }

        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;

        // get real image path by Uri and selection.
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }

        return path;
    }

    private void displayImage(String path) {

        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            picture.setImageBitmap(Bitmap.createScaledBitmap(bitmap,
                    picture.getWidth(), picture.getWidth() * bitmap.getHeight() / bitmap.getWidth(), false));
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
