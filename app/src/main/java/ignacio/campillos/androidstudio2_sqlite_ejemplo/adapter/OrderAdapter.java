package ignacio.campillos.androidstudio2_sqlite_ejemplo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ignacio.campillos.androidstudio2_sqlite_ejemplo.R;

import ignacio.campillos.androidstudio2_sqlite_ejemplo.models.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {

    private Context context;
    private int resources;
    private List<Order> objects;

    public OrderAdapter(Context context, int resources, List<Order> objects) {
        this.context = context;
        this.resources = resources;
        this.objects = objects;
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View orderView = LayoutInflater.from(context).inflate(resources, null);

        orderView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new OrderVH(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        Order order = objects.get(position);

        holder.lbMenu.setText(order.getMenu());
        holder.lbTotal.setText(String.valueOf(order.getTotal()));

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdit(holder.getAdapterPosition()).show();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(holder.getAdapterPosition()).show();
            }
        });
    }

    private AlertDialog confirmEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("EDIT ORDER! :)");
        builder.setCancelable(false);

        View orderView = LayoutInflater.from(context).inflate(R.layout.order_create_model, null);
        EditText txtMenu = orderView.findViewById(R.id.txtMenuOrder_createOrder);
        EditText txtQuantity = orderView.findViewById(R.id.txtQuantity_createOrder);
        EditText txtPrice = orderView.findViewById(R.id.txtPrice_createOrder);
        CheckBox chLarge = orderView.findViewById(R.id.chLarge_createOrder);

        builder.setView(orderView);
        Order order = objects.get(position);

        txtMenu.setText(order.getMenu());
        txtQuantity.setText(String.valueOf(order.getQuantity()));
        txtPrice.setText(String.valueOf(order.getPrice()));
        chLarge.setChecked(order.isLarge());

        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtMenu.getText().toString().isEmpty() ||
                txtQuantity.getText().toString().isEmpty() ||
                txtPrice.getText().toString().isEmpty()){
                    Toast.makeText(context, "MISSING DATA", Toast.LENGTH_SHORT).show();
                }else {
                    objects.set(position,new Order(
                            txtMenu.getText().toString(),
                            chLarge.isChecked(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Float.parseFloat(txtPrice.getText().toString())
                    ));
                    notifyItemChanged(position);
                }
            }
        });
        return builder.create();
    }

    private AlertDialog confirmDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SURE DELETE? :(");
        builder.setCancelable(false);

        builder.setNegativeButton("CANCEL", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(position);
                notifyItemRemoved(position);
            }
        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder {

        TextView lbMenu, lbTotal;
        ImageButton imgEdit, imgDelete;

        public OrderVH(@NonNull View itemView) {
            super(itemView);
            lbMenu = itemView.findViewById(R.id.lbMenu_orderHolder);
            lbTotal = itemView.findViewById(R.id.lbTotal_orderHolder);
            imgDelete = itemView.findViewById(R.id.btnDelete_orderHolder);
            imgEdit = itemView.findViewById(R.id.btnEdit_orderHolder);
        }
    }
}
