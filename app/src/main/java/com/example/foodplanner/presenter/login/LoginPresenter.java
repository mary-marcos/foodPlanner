//package com.example.foodplanner.presenter.login;
//
//import android.content.Intent;
//
//import com.example.foodplanner.view.auth.login.LoginView;
//
//import java.util.Objects;
//
//public class LoginPresenterImp implements LoginPresenter{
//    LoginView loginView;
//    MealRepo mealRepo;
//    private Disposable disposable;
//
//    public LoginPresenterImp(LoginView loginView,MealRepo mealRepo) {
//        this.loginView = loginView;
//        // compositeDisposable=new CompositeDisposable();
//        this.mealRepo=mealRepo;
//    }
//
//    @Override
//    public void signInWithFirebaseAuth(String email, String password) {
//        FirebaseUtils.signIn(email, password, task -> {
//            if(task.isSuccessful()){
//                Constants.CURRENT_USER= FirebaseUtils.getFirebaseInstance().getCurrentUser();
//                loginView.showSuccessMessage();
//            }
//            else{
//                loginView.showErrorMessage(Objects.requireNonNull(task.getException()).getLocalizedMessage());
//            }
//        });
//    }
//
//   /* @Override
//    public void signInWithGoogle(Activity activity) {
//        disposable=mealRepo.signInWithGoogle(activity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).
//                subscribe(user->loginView.
//                        showSuccessGoogleAuthMessage(user),
//                        error->loginView.showFailGoogleAuthMessage(error.getLocalizedMessage()));
//    }*/
//
//
//    @Override
//    public void handleGoogleSignInResult(Intent data) {
//        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//        try {
//            GoogleSignInAccount account = task.getResult(ApiException.class);
//            firebaseAuthWithGoogle(account);
//        } catch (ApiException e) {
//            loginView.showFailGoogleAuthMessage(e.getMessage());
//        }
//    }
//
//    @Override
//    public void signInWithGoogle(GoogleSignInAccount account) {
//        mealRepo.signInWithGoogle(account)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(user -> loginView.showSuccessGoogleAuthMessage(user),
//                        throwable -> loginView.showFailGoogleAuthMessage(throwable.getMessage()));
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        FirebaseUtils.getFirebaseInstance().signInWithCredential(credential)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = FirebaseUtils.getFirebaseInstance().getCurrentUser();
//                        loginView.showSuccessGoogleAuthMessage(user);
//                    } else {
//                        loginView.showFailGoogleAuthMessage(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
//    }
//}
