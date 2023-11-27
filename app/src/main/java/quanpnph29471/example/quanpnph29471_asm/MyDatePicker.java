package quanpnph29471.example.quanpnph29471_asm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import java.util.Calendar;

public class MyDatePicker {

    public static void showDatePicker(Context context, final OnDateSetListener onDateSetListener) {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Khởi tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Gọi callback khi người dùng chọn ngày
                onDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    // Interface để xử lý sự kiện người dùng chọn ngày
    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}

