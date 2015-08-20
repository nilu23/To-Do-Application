package com.example.fnilofar.firstandriodapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 */
public class EditItemDialog extends android.support.v4.app.DialogFragment implements AdapterView.OnItemClickListener {


    private EditText mEditText;
    private Button saveBtn;
    private Items savedItem;
    private int position;

    /**
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditItemDialogListener listener = (EditItemDialogListener) getActivity();
            savedItem.text = mEditText.getText().toString();
            listener.onFinishEditDialog(savedItem, pos);
            dismiss();
            return true;
        }
        return false;

    }**/

   /** @Override
    public void onClick(DialogInterface dialog, int which) {

            EditItemDialogListener listener = (EditItemDialogListener) getActivity();
            savedItem.text = mEditText.getText().toString();
            listener.onFinishEditDialog(savedItem, pos);
            dismiss();

    }**/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        savedItem.text = mEditText.getText().toString();
        listener.onFinishEditDialog(savedItem, position);
        dismiss();
    }

    public interface EditItemDialogListener {
        void onFinishEditDialog(Items editItem, int pos);
    }


    public EditItemDialog() {
        // Required empty public constructor
    }


    public static EditItemDialog newInstance(String title, Items editItem, int pos) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("edit", editItem);
        args.putInt("position", pos);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_edit, container);
        mEditText = (EditText) view.findViewById(R.id.editText);
        saveBtn = (Button) view.findViewById(R.id.savebtn);

        position = (int) getArguments().getInt("position");


        savedItem = (Items) getArguments().getSerializable("edit");
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        mEditText.setText(savedItem.text);
        mEditText.setSelection(mEditText.getText().length());

        saveBtn.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                        savedItem.text = mEditText.getText().toString();
                        listener.onFinishEditDialog(savedItem, position);
                        dismiss();
                    }
                }
        );

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }




}
