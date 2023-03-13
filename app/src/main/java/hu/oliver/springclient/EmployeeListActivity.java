package hu.oliver.springclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import hu.oliver.springclient.Adapter.EmployeeAdapter;
import hu.oliver.springclient.Model.Employee;
import hu.oliver.springclient.Retrofit.EmployeeApi;
import hu.oliver.springclient.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        recyclerView = findViewById(R.id.employeeList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEmployees();
    }

    private void loadEmployees() {
        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);

        employeeApi.getAllEmployees()
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        // response.body(): list of employees
                        // loaded from the server
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        Toast.makeText(EmployeeListActivity.this, "Failed to load employees", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Employee> employeeList) {
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(employeeAdapter);
    }
}