package com.example.appadaper.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appadaper.Reservas.Reserva;
import com.example.appadaper.R;
import com.example.appadaper.ReservasDataManager;

import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ACTIVE = 1;
    private static final int VIEW_TYPE_INACTIVE = 2;

    private List<Reserva> items;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onItemClick(Reserva item, int position);
        void onActionButtonClick(Reserva item, int position);
        void onInfoButtonClick(Reserva item);
        void onDeleteButtonClick(Reserva item, int position);

    }

    public itemAdapter(List<Reserva> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Reserva item = items.get(position);
        return item.isReserve() ? VIEW_TYPE_ACTIVE : VIEW_TYPE_INACTIVE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ACTIVE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_activo, parent, false);
            return new ActiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_inactivo, parent, false);
            return new InactiveViewHolder(view);
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reserva item = items.get(position);


        if (holder instanceof ActiveViewHolder) {
            bindActiveViewHolder((ActiveViewHolder) holder, item, position);

        } else if (holder instanceof InactiveViewHolder) {
            bindInactiveViewHolder((InactiveViewHolder) holder, item, position);

        }
    }

    private void bindActiveViewHolder(ActiveViewHolder holder, Reserva item, int position) {
        holder.tvTitle.setText("#"+String.valueOf(item.getCodigo()));
        holder.tvDescription.setText("PRECIO: "+String.valueOf(item.getPrecioTotal())+"$");
        Glide.with(holder.itemView.getContext())
                .load(item.getUrlImagen())
                .centerCrop()
                .into(holder.ivImage);

        LinearLayout cardView = holder.itemView.findViewById(R.id.cardViewContainer);
        int color = Color.parseColor(item.getCardColor());
        cardView.setBackgroundColor(color);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, position);
            }
        });
        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionButtonClick(item, position);
            }
        });
        holder.btnInfo.setOnClickListener(v -> {
            if (listener != null) {
                listener.onInfoButtonClick(item);
            }
        });
        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteButtonClick(item, position);
            }
        });
    }

    private void bindInactiveViewHolder(InactiveViewHolder holder, Reserva item, int position) {
        holder.tvTitle.setText("#"+String.valueOf(item.getCodigo()));
        holder.tvDescription.setText("ESPERANZA DE VIDA: "+String.valueOf(item.getEsperanzaVida()));
        Glide.with(holder.itemView.getContext())
                .load(item.getUrlImagen())
                .centerCrop()
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, position);
            }
        });

        holder.btnAction.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionButtonClick(item, position);
            }
        });
        holder.btnInfo.setOnClickListener(v -> {
            if (listener != null) {
                listener.onInfoButtonClick(item);
            }
        });
        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteButtonClick(item, position);
            }
        });
    }
    public void removeItem(String codigo) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCodigo().equals(codigo)) {
                items.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Reserva> newItems) {
        this.items = newItems;
    }
    public void addItem(Reserva nuevaReserva) {
        // Añadimos la nueva reserva al singleton
        ReservasDataManager.getInstance().getReservas().add(nuevaReserva);

        // Actualizamos la lista del adaptador desde el singleton
        this.items = ReservasDataManager.getInstance().getReservas();  // Usamos 'items', que es la lista interna

        // Notificamos al RecyclerView que se insertó un nuevo item
        notifyItemInserted(items.size() - 1);  // Indicar que el nuevo ítem fue agregado
    }
    public static class ActiveViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivImage;
        ImageButton btnAction;
        ImageButton btnInfo;
        ImageButton btnEliminar;

        public ActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnAction = itemView.findViewById(R.id.btnAction);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    public static class InactiveViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivImage;
        ImageButton btnAction;
        ImageButton btnInfo;
        ImageButton btnEliminar;

        public InactiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnAction = itemView.findViewById(R.id.btnAction);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
