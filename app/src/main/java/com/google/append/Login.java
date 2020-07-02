package com.google.append;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    EditText edtGmail, edtMatKhau, edtGmailDangky, edtNameDK ,edtPassword, edtPassword2 ,edtPhonemunber;
    Button btlogin, btdangky, btavatarDk, btHuyDangKy ,btDangkyDK , bts ;
    ImageView imgavatarDangKy;
    TextView txtTaiDay, a;
    CheckBox checkBoxDangKy;
    Toast backToast;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    ConstraintLayout constraintlayout1,con11,constraintlayout2;
    private long backPressedTime;
    private FirebaseAuth mAuth;
    private static final String TAG= " ";
    CallbackManager callbackManager;
    LoginButton loginButton;
    String last_name,email,id,linkavatar;
    int code = 1;
    ProfilePictureView profilePictureView;
    DatabaseReference mdatafirebaselogin, mdatafirebasex;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    static  String  sdt  ,sID;
    static boolean bCheckUser = false;
    private  static  final int RC_CODE = 9001;
     int counte = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // ánh xạ
        inten();
        // login google
        SignInButton signInButton = findViewById(R.id.sign_in_button1);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // đăng xuất trước khi vao App
        signOut();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signInGoogle();
                    Log.d("Login", "11111111");
                }catch (Exception e){
                    //
                }
            }
        });

        ///
        //facebook login
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        setloginfacebook();
//        database firebase
        mdatafirebaselogin= FirebaseDatabase.getInstance().getReference();
        // Storrage
//        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-app-8e444.appspot.com/img");


        timee=new Timer();
        timee.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {counte++;
                         if (counte==2){
                             con11.setVisibility(View.GONE);
                             constraintlayout1.setVisibility(View.VISIBLE);
                         }
                    }
                });
            }
        }, 1000, 1000);

    }

    private void addProfile(String key, String avatar, String email, String name, String phone){
        final ProfileUser profileUser = new ProfileUser(avatar,email,name,phone);
        mdatafirebaselogin.child("profileuser/"+key).setValue(profileUser);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntents = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntents, RC_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // login facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == code && resultCode == RESULT_OK && data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgavatarDangKy.setImageBitmap(photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
        // login google
        if (requestCode == RC_CODE) {
            Task<GoogleSignInAccount> taskGogole = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(taskGogole);
        }
    }
    public void checkUserAdd (final String id, final String savatar, final String semail, final String sname, final String sphone){
        mdatafirebasex = FirebaseDatabase.getInstance().getReference().child("profileuser").child(id);
        mdatafirebasex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProfileUser profileUser = dataSnapshot.getValue(ProfileUser.class);
                if (profileUser == null){
                    // add profile
                    addProfile(id,savatar,semail,sname,sphone);
                    mdatafirebasex.removeEventListener(this);
                    bCheckUser= true;
                }else {
                    /// profile giữa SDT
                    addProfile(id,savatar,semail,sname,profileUser.Phonenumber);
                    mdatafirebasex.removeEventListener(this);
                    bCheckUser= true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount accountGoogle = completedTask.getResult(ApiException.class);
            updateUIGoogle(accountGoogle);
            // check uer & add profile

            checkUserAdd(accountGoogle.getId(),accountGoogle.getPhotoUrl().toString(),accountGoogle.getEmail(),accountGoogle.getDisplayName()
                                        ,"");
            // chuyển Activity

                TosendActivity(accountGoogle.getId()+"");

        } catch (ApiException e) {
            Log.w("qqq", "signInResult:failed code=" + e.getStatusCode());
            updateUIGoogle(null);
        }
    }

    private void updateUIGoogle(GoogleSignInAccount account) {
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Log.d("Login", "555555555");
        GoogleSignInAccount accountGoogle = GoogleSignIn.getLastSignedInAccount(this);
        updateUIGoogle(accountGoogle);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    private void createUser(){
        String email = edtGmailDangky.getText().toString();
        String password = edtPassword2.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                addProfile(user.getUid(),"link",edtGmailDangky.getText().toString(),edtNameDK.getText().toString(),edtPhonemunber.getText().toString());
                                updateUI(null);
                                Toast.makeText(Login.this, "Đăng ký Thành công", Toast.LENGTH_SHORT).show();
                                constraintlayout2.setVisibility(View.GONE);
                                constraintlayout1.setVisibility(View.VISIBLE);
                                edtGmail.setText(edtGmailDangky.getText().toString());
                        }else
                            Toast.makeText(Login.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // set edt null
    public void SetNull (){
        edtGmail.setText(null);
        edtMatKhau.setText(null);
        edtGmailDangky.setText(null);
        edtNameDK.setText(null);
        edtPassword.setText(null);
        edtPassword2 .setText(null);
        edtPhonemunber.setText(null);
    }
    public void  getProfile(String s){
        mdatafirebasex = FirebaseDatabase.getInstance().getReference().child("profileuser").child(s);
        //  ea = "6eLrmgHUESVBpFZrPEPPyjUupYZ2";
        sID = s;
        mdatafirebasex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileUser profileUser = dataSnapshot.getValue(ProfileUser.class);
                sdt = profileUser.Phonenumber;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void TosendActivity (final String id){
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("KeyID",id);
        T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {countime++;
                        if (countime==1 ){
                            startActivity(intent);
                            T.cancel();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }
    ////////// sự kiện
    public void eventlick (View view) {
        switch (view.getId()) {
            case R.id.btdangky:
                constraintlayout1.setVisibility(View.GONE);
                constraintlayout2.setVisibility(View.VISIBLE);
                SetNull();
                break;
            case R.id.btHuyDangKy:
                constraintlayout2.setVisibility(View.GONE);
                constraintlayout1.setVisibility(View.VISIBLE);
                SetNull();
                break;
            case R.id.btDangkyDK:

                if (edtGmailDangky.getText().toString().equals(""))
                    Toast.makeText(this, "Chưa nhập Emial", Toast.LENGTH_SHORT).show();
                else if (edtNameDK.getText().toString().equals(""))
                    Toast.makeText(this, "Chưa nhập tên", Toast.LENGTH_SHORT).show();
                else if (edtPhonemunber.getText().toString().equals(""))
                    Toast.makeText(this, "Chưa nhập điện điện thoại", Toast.LENGTH_SHORT).show();
                else if (edtPassword.getText().toString().equals("")&& edtPassword2.getText().toString().equals(""))
                    Toast.makeText(this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                else if (edtPassword.getText().toString().equals(edtPassword2.getText().toString())){
                    if (checkBoxDangKy.isChecked()){
                         createUser();
                    }else
                        Toast.makeText(this, "Chưa đồng ý các quy định", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btlogin:
                Sigin();

                break;
        }
    }
    /// sign firebase
    private void Sigin() {
        String email = edtGmail.getText().toString();
        String password = edtMatKhau.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            TosendActivity(user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    /////////// anh xa item
    private void inten() {
        edtGmail = findViewById(R.id.edtGmail);
        edtMatKhau= findViewById(R.id.edtMatKhau);
        edtGmailDangky= findViewById(R.id.edtGmailDangky);
        edtNameDK= findViewById(R.id.edtNameDK);
        edtPassword= findViewById(R.id.edtPassword);
        edtPassword2= findViewById(R.id.edtPassword2);
        edtPhonemunber= findViewById(R.id.edtPhonemunber);
        btlogin= findViewById(R.id.btlogin);
        btdangky= findViewById(R.id.btdangky);
        btavatarDk= findViewById(R.id.btavatarDk);
        con11= findViewById(R.id.con11);
        btHuyDangKy= findViewById(R.id.btHuyDangKy);
        btDangkyDK= findViewById(R.id.btDangkyDK);
        imgavatarDangKy= findViewById(R.id.imgavatarDangKy);
        txtTaiDay= findViewById(R.id.txtTaiDay);
        checkBoxDangKy= findViewById(R.id.checkBoxDangKy);
        constraintlayout1= findViewById(R.id.constraintlayout1);
        constraintlayout2= findViewById(R.id.constraintlayout2);
        a= findViewById(R.id.a);
        checkBoxDangKy= findViewById(R.id.checkBoxDangKy);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void setloginfacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // đăng nhập facebook thành công code
                inputprofilefacebook();
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }
    Timer T , timee;
    int countime=0;
    private void inputprofilefacebook() { // link https://www.youtube.com/watch?v=oSuZNkOSGRE&list=PLzrVYRai0riSVwL5cv7JaC4aOJjjfSMfv&index=5
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(final JSONObject object, GraphResponse response) {
                Log.d("profile", response.getJSONObject().toString());
                getProfile(Profile.getCurrentProfile().getId());
                if (sdt=="") {
                    try {
                        last_name = object.getString("last_name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        email = object.getString("email");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    id = Profile.getCurrentProfile().getId();
                    linkavatar = "https://graph.facebook.com/" +id+ "/picture?type=large";
                    addProfile(id,linkavatar,email,last_name,"");
                    TosendActivity(Profile.getCurrentProfile().getId());
                    T.cancel();
                }else
                {
                    TosendActivity(Profile.getCurrentProfile().getId());

                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
    /// click back
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "click back", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
