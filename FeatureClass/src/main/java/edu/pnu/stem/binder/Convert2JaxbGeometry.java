package edu.pnu.stem.binder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import edu.pnu.stem.geometry.jts.Solid;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.ObjectFactory;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.ShellType;
import net.opengis.gml.v_3_2_1.SolidType;
import net.opengis.gml.v_3_2_1.SurfaceType;

public class Convert2JaxbGeometry {
	ObjectFactory gmlFactory;
	
	public SolidType Convert2SolidType(Solid feature){
		MultiPolygon shell = feature.getShell();
		SolidType newFeature = gmlFactory.createSolidType();
		Convert2ShellType(shell);
		return newFeature;
	}
	
	public ShellType Convert2ShellType(MultiPolygon feature){
		List<Polygon>polygonList = new ArrayList<Polygon>();
		ShellType newFeature = gmlFactory.createShellType();
		for(int i = 0 ; i < feature.getLength() ; i++){
			polygonList.add((Polygon)feature.getGeometryN(i));
		}
		return newFeature;
	}
	
	public SurfaceType Convert2SurfaceType(Polygon feature){
		Convert2LinearRingType((LinearRing)feature.getExteriorRing());
		SurfaceType newFeature = gmlFactory.createSurfaceType();
		return newFeature;
	}
	public LinearRingType Convert2LinearRingType(LinearRing feature){
		List<Point>pointList = new ArrayList<Point>();
		LinearRingType newFeature = gmlFactory.createLinearRingType();
		for(int i = 0 ; i < feature.getLength() ; i++){
			pointList.add(feature.getPointN(i));
		}
		return newFeature;
	}
	public LineStringType Convert2LineStringType(LineString feature){
		LineStringType newFeature = gmlFactory.createLineStringType();
		DirectPositionListType directPost = gmlFactory.createDirectPositionListType();
		List<Coordinate>coordList = new ArrayList<Coordinate>();
		for(int i = 0 ; i < feature.getCoordinates().length;i++){
			
		}
		
		return newFeature;
	}
	
	public PointType Convert2PointType(Point feature){
		PointType newFeature = gmlFactory.createPointType();
		newFeature.setPos(Convert2CoordinateType(feature.getCoordinateSequence().getCoordinate(0), feature.getDimension()));
		return newFeature;
	}
	public DirectPositionListType Convert2DirectPositionType(Coordinate[] feature){
		DirectPositionListType newFeature = gmlFactory.createDirectPositionListType();
		return newFeature;
	}
	public DirectPositionType Convert2CoordinateType(Coordinate feature, int dimension){
		DirectPositionType newFeature = gmlFactory.createDirectPositionType();
		if(dimension == 2){
			List<Double> points = new ArrayList<Double>();
			points.add(feature.x);
			points.add(feature.y);
			newFeature.setValue(points);
		}
		else if(dimension == 3){
			List<Double>points = new ArrayList<Double>();
			points.add(feature.x);
			points.add(feature.y);
			points.add(feature.z);
			newFeature.setValue(points);
		}
		BigInteger d = BigInteger.valueOf((long)dimension);
		newFeature.setSrsDimension(d);
		return newFeature;
	}
	
	
}
