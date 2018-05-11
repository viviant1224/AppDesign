package com.viviant.earngold.widget.recyclerView;

public interface MultiItemTypeSupport<T>
{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}