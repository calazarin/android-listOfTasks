package androidfactory.lazarinapps.com.listoftasks.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidfactory.lazarinapps.com.listoftasks.R;
import androidfactory.lazarinapps.com.listoftasks.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter( List<Task> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_adapter, parent, false);
        return new TaskViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task.setText(this.taskList.get(position).getTaskName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView task;

        public TaskViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById( R.id.taskDescription);
        }
    }

}