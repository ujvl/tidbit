package com.thetidbitapp.tidbit;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;
import com.thetidbitapp.model.SessionManager;
import com.thetidbitapp.util.InternetUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class NewEventFragment extends Fragment implements View.OnClickListener,
														  GraphRequest.Callback {

    private OnSubmitListener mListener;

    private Button mDateButton, mFromTimeButton, mToTimeButton;
    private FloatingActionButton mFABUpload;
    private ImageView mCoverImage;

    private static final int IMAGE_REQUEST = 1;

    public interface OnSubmitListener {
        public void onSubmit();
    }

    public NewEventFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_event, container, false);

        mDateButton = (Button) root.findViewById(R.id.date_button);
        mFromTimeButton = (Button) root.findViewById(R.id.from_time_button);
        mToTimeButton = (Button) root.findViewById(R.id.to_time_button);
        mFABUpload = (FloatingActionButton) root.findViewById(R.id.fab_upload);
        mCoverImage = (ImageView) root.findViewById(R.id.create_event_cover);

        mFABUpload.setOnClickListener(this);
        mCoverImage.setOnClickListener(this);

        setupButtonListener(mDateButton, true);
        setupButtonListener(mFromTimeButton, false);
        setupButtonListener(mToTimeButton, false);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_event, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mListener.onSubmit();
            return true;
        }
		if (item.getItemId() == R.id.action_import) {
			getEventListFromFacebook();
			return true;
		}
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onCompleted(GraphResponse graphResponse) {
		Log.e("On completed", graphResponse.getJSONObject().toString());
	}

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Uri selectedImageUri = data.getData();
                ParcelFileDescriptor pfd = getActivity()
						.getContentResolver().openFileDescriptor(selectedImageUri, "r");
                FileDescriptor imageSource = pfd.getFileDescriptor();
                Bitmap cover = BitmapFactory.decodeFileDescriptor(imageSource);
                mCoverImage.setImageBitmap(cover);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSubmitListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Parent must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("New Event");
    }

	/**
	 * Attempts to get a list of the user's visible
	 * events from his/her facebook
	 */
	private void getEventListFromFacebook() {
		if (InternetUtil.isOnline(getActivity())) {

			SessionManager manager = new SessionManager(getActivity());
			AccessToken token = manager.getAccessToken();
			String path = manager.getString(getString(R.string.fb_field_id)) + "/events";
			Bundle params = new Bundle();
			params.putString(getString(R.string.fb_fields_key), getString(R.string.fb_ev_fields));

			GraphRequest request = GraphRequest.newGraphPathRequest(token, path, this);
			request.setParameters(params);
			request.executeAsync();

		}
		else {
			Snackbar.make(getView(), getString(R.string.connect_error), Snackbar.LENGTH_SHORT).show();
		}
	}

	/**
	 * Sets up listener for button
	 * @param button Button to attach listener to
	 * @param isForDate uses date dialog if true, time dialog otherwise
	 */
	private void setupButtonListener(final Button button, boolean isForDate) {

		Calendar now = Calendar.getInstance();
		final DialogFragment picker;

		if (isForDate) {
			picker = DatePickerDialog.newInstance(
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
							button.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
						}
					},
					now.get(Calendar.YEAR),
					now.get(Calendar.MONTH),
					now.get(Calendar.DAY_OF_MONTH)
			);
		}
		else {
			picker = TimePickerDialog.newInstance(
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
							button.setText(hourOfDay + ":" + minute);
						}
					},
					now.get(Calendar.HOUR_OF_DAY),
					now.get(Calendar.MINUTE),
					false
			);
		}

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				picker.show(getActivity().getFragmentManager(), "");
			}
		});

	}

}
