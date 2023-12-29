package quanpnph29471.example.quanpnph29471_asm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    // Lớp để so sánh hai ngày

    public static boolean isDate1BeforeDate2(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parsedDate1 = sdf.parse(date1);
            Date parsedDate2 = sdf.parse(date2);

            return parsedDate1.before(parsedDate2)||parsedDate1.equals(parsedDate2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xử lý ngày tháng
        }
    }
    public static int getStatusByDate(String current,String start, String end) {
        try {
            if(isDate1BeforeDate2(current,start)){
                return 0;
            }else if(isDate1BeforeDate2(start,current)&&isDate1BeforeDate2(current,end)){
                return 1;
            }else if(isDate1BeforeDate2(end,current)){
                return 2;
            }else return -1;

        } catch (Exception e) {
            e.printStackTrace();
            return 10;
        }
    }

    public String getCurrentDate() {
        // Lấy thời gian hiện tại
        Date currentDate = new Date();

        // Định dạng thời gian theo "yyyy/MM/dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        // Chuyển đổi thời gian thành chuỗi theo định dạng
        return dateFormat.format(currentDate);
    }
}



