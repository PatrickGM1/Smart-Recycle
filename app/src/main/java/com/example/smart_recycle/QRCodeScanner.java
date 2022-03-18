package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.concurrent.TimeUnit;

public class QRCodeScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Boolean verif=false;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        scannView= findViewById(R.id.scanner_view);
        codeScanner= new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultdata);
        mDatabase = FirebaseDatabase.getInstance().getReference("GarbageMarkers");

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    String copil = child.getKey().trim();
                                    String rez= result.getText().trim();
                                    if (copil.equals(rez)) {
                                        startActivity(new Intent(QRCodeScanner.this,Succes.class));
                                        verif=true;
                                        break;
                                    }
                                }
                                if(verif==false){
                                    Toast.makeText(QRCodeScanner.this, "Invalid QR Code", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(QRCodeScanner.this, QRCodeScanner.class));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}