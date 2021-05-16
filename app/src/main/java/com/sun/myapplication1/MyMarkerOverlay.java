package com.sun.myapplication1;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MyMarkerOverlay extends Overlay {
    /**
     * 基本属性
     */
    private Context context;//cons
    private MapView mMapView;//cons
    private OverlayItem overlayItem;
    private Point point;
    private GeoPoint geo;
    private ItemizedOverlayWithFocus itemItemizedOverlayWithFocus;
    private ArrayList<OverlayItem> overlayItems = new ArrayList<>();
    /**
     * 传入context
     * constructrue
     */
    public MyMarkerOverlay(Context context,MapView mapView){
        super(context);
        this.context = context;
        mMapView = mapView;
    }
    //地图点击事件
    @Override
    public boolean onSingleTapUp(MotionEvent e, final MapView mapView) {
        Projection projection = mMapView.getProjection();
        point = new Point((int)e.getX(),(int)e.getY());
        geo = (GeoPoint) ((Projection) projection).fromPixels((int)e.getX(),(int)e.getY());
        //初始化overlayitem
        overlayItem = new OverlayItem("test","MARKERITEM",geo);
        overlayItem.setMarker(context.getResources().getDrawable(R.drawable.marker_default));
        //添加进数组，添加进图层
        overlayItems.add(overlayItem);
        /**
         * do something
         */
        //新建图层，添加标记
        itemItemizedOverlayWithFocus = new ItemizedOverlayWithFocus(
                overlayItems,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        mapView.setExpectedCenter(geo);
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context
        );
        /**
         * 点击一个点之后结束本事件监听图层,如果想要继续添加的话可以进行改动
         */
        removeThis();
        /**
         * 将该itmizedoverlay添加到地图
         */
        drawMarker();
        return super.onSingleTapUp(e, mapView);
    }


    public void removeThis(){
        mMapView.getOverlays().remove(this);
    }
    /**
     * 将itmizedoverly添加到地图上
     * 并添加到图层管理
     * 12.25未完成图层管理
     */
    public void drawMarker(){
        mMapView.getOverlays().add(itemItemizedOverlayWithFocus);
        //mMapView.getOverlayManager().remove(itemItemizedOverlayWithFocus);
    }

}
