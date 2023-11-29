package ignacio.campillos.androidstudio2_sqlite_ejemplo;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import ignacio.campillos.androidstudio2_sqlite_ejemplo.adapter.OrderAdapter;
import ignacio.campillos.androidstudio2_sqlite_ejemplo.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio2_sqlite_ejemplo.models.Order;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Order> listOrder;
    private ActivityMainBinding binding;

    private OrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

     listOrder = new ArrayList<>();

     adapter = new OrderAdapter(MainActivity.this, R.layout.order_holder_model, listOrder);
     layoutManager = new LinearLayoutManager(this);

     binding.contentMain.container.setAdapter(adapter);
     binding.contentMain.container.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder().show();
            }
        });
    }

    private AlertDialog createOrder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("CRETE ORDER");
        builder.setCancelable(false);

        View orderView = LayoutInflater.from(this).inflate(R.layout.order_create_model, null);
        EditText txtMenu = orderView.findViewById(R.id.txtMenuOrder_createOrder);
        CheckBox chLarge = orderView.findViewById(R.id.chLarge_createOrder);
        EditText txtQuantity = orderView.findViewById(R.id.txtQuantity_createOrder);
        EditText txtPrice = orderView.findViewById(R.id.txtPrice_createOrder);

        builder.setView(orderView);

        builder.setNegativeButton("CANCEL", null);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!txtMenu.getText().toString().isEmpty() &&
                !txtQuantity.getText().toString().isEmpty() &&
                !txtPrice.getText().toString().isEmpty()){
                    Order order = new Order(
                            txtMenu.getText().toString(),
                            chLarge.isChecked(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Float.parseFloat(txtPrice.getText().toString())
                    );

                    listOrder.add(order);
                    adapter.notifyItemInserted(listOrder.size()-1);
                }
            }
        });

        return builder.create();
    }
}