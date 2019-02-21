package lukeeey.drivejsy.adapters;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import lukeeey.drivejsy.R;
import lukeeey.drivejsy.models.carpark.Carpark;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class CarparksAdapter extends RecyclerView.Adapter<CarparksAdapter.ViewHolder> {

    private List<Carpark> carparks;

    public CarparksAdapter(List<Carpark> carparks) {
        this.carparks = carparks;
    }

    @Override
    public CarparksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View carparkView = inflater.inflate(R.layout.carpark_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(carparkView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CarparksAdapter.ViewHolder viewHolder, int position) {
        Carpark carpark = carparks.get(position);

        TextView name = viewHolder.carparkName;
        name.setText(carpark.name);

        TextView spaces = viewHolder.carparkSpaces;
        spaces.setText(Html.fromHtml("<html><body><font size=21>" + String.valueOf(carpark.spaces) + "</font> spaces</body></html>"));

        TextView capacity = viewHolder.carparkCapacity;
        capacity.setText("20% full");
    }

    @Override
    public int getItemCount() {
        return carparks.size();
    }

    public void addItem(Carpark carpark) {
        carparks.add(carpark);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView carparkName;
        public TextView carparkSpaces;
        public TextView carparkCapacity;

        public ViewHolder(View view) {
            super(view);
            carparkName = (TextView) view.findViewById(R.id.carpark_name);
            carparkSpaces = (TextView) view.findViewById(R.id.carpark_spaces);
            carparkCapacity = (TextView) view.findViewById(R.id.carpark_capacity);
        }
    }
}
