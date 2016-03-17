package temnogrudova.com.retrofittest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
/**
 * Реализация класса ViewHolder, хранящего ссылки на визуальные компоненты.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView txtUserId;
    private TextView txtId;
    private TextView txtTitle;
    private TextView txtBody;

    public ItemViewHolder(final View parent, TextView txtUserId, TextView txtId, TextView txtTitle, TextView txtBody) {
        super(parent);
        this.txtUserId = txtUserId;
        this.txtId = txtId;
        this.txtTitle = txtTitle;
        this.txtBody = txtBody;
    }

    public static ItemViewHolder newInstance(View parent) {
        TextView txtUserId = (TextView)parent.findViewById(R.id.user_id);
        TextView txtId = (TextView)parent.findViewById(R.id.id);
        TextView txtTitle = (TextView)parent.findViewById(R.id.title);
        TextView txtBody = (TextView)parent.findViewById(R.id.body);
        return new ItemViewHolder(parent, txtUserId, txtId, txtTitle, txtBody);
    }

    public void setItemText( String userId, String id, String title, String body) {
        txtUserId.setText(userId);
        txtId.setText(id);
        txtTitle.setText(title);
        txtBody.setText(body);
    }
}

