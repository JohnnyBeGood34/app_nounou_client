package Adapteur;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.example.nounou.R;
import com.example.nounou.UrlServer;
import com.example.nounou.data.ApiNounou;
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
	
	String URL = UrlServer.getServerUrl();
	Context _ctx;
	ArrayList<Nounou> _list;
	LayoutInflater _inflater;
	static RequestQueue _volleyQueue; // File d'attente volley
	
	public NounouAdapter(Context ctx,ArrayList al) {
		
		final NounouBdd db=new NounouBdd(ctx);
		db.open();
		this._ctx = ctx;
		this._list = al;//(ArrayList<Nounou>) db.getAllNounou();
		this._inflater = (LayoutInflater) this._ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void notifyAdapter(ArrayList<Nounou> nounous)
	{
		this._list = nounous;
	}
	
	public Nounou getNounouAtIndex(int index)
	{
		return _list.get(index);
	}
	
	public void setFilter(String filter) {
		
	}
	
	public ArrayList<Nounou> getList()
	{
		return this._list;
	}
	
	@Override
	public int getCount() {		
		return this._list.size();
	}

	@Override
	public Object getItem(int position) {		
		return this._list.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public int getItemViewType(int position) {		
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Nounou nounou = (Nounou) this.getItem(position);		
		CustomNounouHolder holder;
		
		if (convertView == null) {
			
			holder = new CustomNounouHolder();
			convertView = this._inflater.inflate(R.layout.cell_nounou, null);
			holder.nom = (TextView)convertView.findViewById(R.id.textViewNom);
			holder.email = (TextView)convertView.findViewById(R.id.textViewEmail);
			holder.image = (ImageView)convertView.findViewById(R.id.imageViewPhoto);
			holder.distance=(TextView)convertView.findViewById(R.id.distance);
			convertView.setTag(holder);
		}
		else {
			holder = (CustomNounouHolder) convertView.getTag();
		}
		
		holder.nom.setText(nounou.getNom());
		holder.email.setText(nounou.getEmail());
		holder.distance.setText(nounou.getDistance());
		ApiNounou.getImageFromUrl(URL+nounou.getCheminPhoto(), holder.image, this._ctx);
		return convertView;
	}

	@Override
	public int getViewTypeCount() {		
		return 1;
	}

	@Override
	public boolean hasStableIds() {		
		return false;
	}

	@Override
	public boolean isEmpty() {		
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
		TextView distance;
	}
}
