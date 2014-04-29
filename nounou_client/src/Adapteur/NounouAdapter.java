package Adapteur;

import java.util.ArrayList;

import com.example.nounou.R;
import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class NounouAdapter implements ListAdapter {
	Context _ctx;
	ArrayList<Nounou> _list;
	LayoutInflater _inflater;

	public NounouAdapter(Context ctx) {
		final NounouBdd db=new NounouBdd(ctx);
		db.open();

		this._ctx = ctx;
		this._list = (ArrayList<Nounou>) db.getAllNounou();
		this._inflater = (LayoutInflater) this._ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public Nounou getNounouAtIndex(int index)
	{
		return _list.get(index);
	}
	
	public void setFilter(String filter) {
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this._list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this._list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Nounou nn = (Nounou) this.getItem(position);
		
		CustomNounouHolder holder;
		if (convertView == null) {
			holder = new CustomNounouHolder();
			convertView = this._inflater.inflate(R.layout.cell_nounou, null);
			holder.nom = (TextView)convertView.findViewById(R.id.textViewNom);
			holder.email = (TextView)convertView.findViewById(R.id.textViewEmail);
			holder.image = (ImageView)convertView.findViewById(R.id.imageViewPhoto);
			convertView.setTag(holder);
		} else {
			holder = (CustomNounouHolder) convertView.getTag();
		}
		
		holder.nom.setText(nn.getNom());
		holder.email.setText(nn.getEmail());
		
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

	private class CustomNounouHolder {
		TextView email;
		TextView nom;
		ImageView image;
	}
}
