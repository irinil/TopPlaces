package gr.aueb.cs.project.ds.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p3130269 on 24/5/2016.
 */
public class Intro extends Activity {

    private ProgressDialog progressdialog=null;
    Information inform=null;
    Integer portnew=0;
    String ipnew="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);



        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Loading...");

        Button info = (Button) findViewById(R.id.button3);

        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent myinten = new Intent(view.getContext(), Popup.class);
                startActivityForResult(myinten, 0);
            }


        });




    }

    public void open(View view) {

        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_box, null);

        final EditText port = (EditText) rootView.findViewById(R.id.port);
        final EditText ip = (EditText) rootView.findViewById(R.id.ip);
        final EditText latidudemin = (EditText) rootView.findViewById(R.id.latmin);
        final EditText longtiduemin = (EditText) rootView.findViewById(R.id.longtmin);
        final EditText latidudemax = (EditText) rootView.findViewById(R.id.latmax);
        final EditText longtiduemax = (EditText) rootView.findViewById(R.id.longtmax);
        final EditText fromdate = (EditText) rootView.findViewById(R.id.datefrom);
        final EditText todate = (EditText) rootView.findViewById(R.id.dateto);
        final EditText fromtime = (EditText) rootView.findViewById(R.id.timefrom);
        final EditText totime = (EditText) rootView.findViewById(R.id.timeto);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enter Details:");
        alertDialogBuilder.setView(rootView);
        alertDialogBuilder.setCancelable(false);



        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                inform = new Information(Double.valueOf(latidudemin.getText().toString()),Double.valueOf(longtiduemin.getText().toString()),Double.valueOf(latidudemax.getText().toString()),
                        Double.valueOf(longtiduemax.getText().toString()),fromdate.getText().toString(),fromtime.getText().toString(),todate.getText().toString(),totime.getText().toString(),10);

                portnew =Integer.valueOf( port.getText().toString());
                ipnew =  ip.getText().toString();

                progressdialog.show();
                new BackroundJob().execute();


            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }





    private class BackroundJob extends AsyncTask<Object, Object, ArrayList<TopPlaceInformation>> {


        @Override
        protected void onPreExecute() {
            String nowip=ipnew;
            Integer nowport=portnew;
        }


        @Override
        protected ArrayList<TopPlaceInformation> doInBackground(Object... params) {
            Socket requestSocket = null;
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            ServerMessage message =null;
            ArrayList<TopPlaceInformation> placelist=new ArrayList<>();

            try {
                requestSocket = new Socket(ipnew, portnew);
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                in = new ObjectInputStream(requestSocket.getInputStream());
                out.writeObject(inform);
                out.flush();

                try {
                    message =(ServerMessage)in.readObject();
                    placelist = (ArrayList<TopPlaceInformation>) message.getPlaces();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            } catch (UnknownHostException unknownHost) {
                System.err.println("You are trying to connect to an unknown host!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    requestSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            return placelist;
        }

        @Override
        protected void onPostExecute (ArrayList<TopPlaceInformation> result){

            Intent intent = new Intent(Intro.this, TabsMenu.class);
            intent.putExtra("TopPlaceInformation",result);
            startActivity(intent);
            startActivityForResult(intent, 0);
            super.onPostExecute(result);

            progressdialog.dismiss();

        }





    }



}
