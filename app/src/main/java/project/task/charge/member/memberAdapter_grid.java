package shop.carate.shopper.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import shop.carate.shopper.R;
import shop.carate.shopper.util.Global;


public class memberAdapter_grid extends ArrayAdapter <Member>{


    ArrayList<Member> array_all_members = new ArrayList<>();

    public memberAdapter_grid(Context context, int textViewResourceId, ArrayList<Member> objects) {
        super(context, textViewResourceId, objects);
        array_all_members = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_user_grid, null);
        TextView user_name = (TextView) v.findViewById(R.id.user_name);
        RoundedImageView user_photo = (RoundedImageView) v.findViewById(R.id.roundedImageViewLovesItemLovesImage);
        user_name.setText(array_all_members.get(position).getName());
        if(array_all_members.get(position).getGender().equals("Male"))
            user_photo.setImageResource(R.drawable.man_dummy);
        else
            user_photo.setImageResource(R.drawable.woman_dummy);

        return v;

    }
}