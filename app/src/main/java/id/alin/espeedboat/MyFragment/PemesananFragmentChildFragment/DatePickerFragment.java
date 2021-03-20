package id.alin.espeedboat.MyFragment.PemesananFragmentChildFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananData;
import id.alin.espeedboat.R;

public class DatePickerFragment extends DialogFragment implements LifecycleOwner {
    private DatePicker datePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.datePicker = view.findViewById(R.id.datepickerPemesananFragment);
        this.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                try {
                    LocalDate date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                    DateTimeFormatter formatervariable = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter formaterdisplay = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
                    String displaydate = date.format(formaterdisplay);
                    String variabledate = date.format(formatervariable);

                    PemesananData pemesananData = MainActivity.mainActivityViewModel.getPemesananLiveData().getValue();
                    pemesananData.setTanggal(displaydate);
                    pemesananData.setTanggal_variable(variabledate);
                    MainActivity.mainActivityViewModel.setPemesananData(pemesananData);
                    dismiss();
                } catch (DateTimeException ignored) {
                    dismiss();
                    Snackbar.make(getActivity().findViewById(R.id.parentlayoutMainActivity), "TANGGAL TIDAK VALID", 3000)
                            .setAnchorView(R.id.botnavbarMainActivity)
                            .setBackgroundTint(ContextCompat.getColor(getContext(), R.color.dangerColor))
                            .show();
                }
            }
        });
    }

}