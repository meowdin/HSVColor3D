package hsvcolor3d;

import java.lang.Float;

public class ColorSpace {
float r;
float g;
float b;
/*This class takes in three color component R,G,and B
 * and generate the HSV and HSL value
 * This value is then furhter on used to generate the
 * cartesian coordinates X,Y and Z
 * 
 * The HSL coordination is a single cone instead of a double
 * as the value of L is kinda hard to handle.
 * 
 * You are free to modify this codes... so hopefully
 * the HSL double cone is coming out someday
 * 
 * Please refer to the LICENSE.txt for the Java3d license
 * details. 
 * HSV and HSL calculation detail could be found : https://en.wikipedia.org/wiki/HSL_and_HSV
 * */
ColorSpace (float i,float j,float k){
	r=i;
	g=j;
	b=k;
	}	

//Calculate the max and min values of the RGB component
public float max(){
	return Math.max(Math.max(r,g),Math.max(g,b));
}
public float min(){
	return Math.min(Math.min(r,g),Math.min(g,b));
}



//Methods for calculate the H,S,V and L values
public float h(){
if (this.max()==this.min()){
	return 0;
}	
else if(max()==g){
	return (60*((b-r)/(this.max()-this.min())))+120;
}
else if(this.max()==b){
	return (60*((r-g)/(this.max()-this.min())))+240;
	
}	
else{
	float temphr= (60*((g-b)/(this.max()-this.min())))%360;
	if(temphr>360){
		return temphr-360;
	}else if(temphr<0){
		return temphr+360;
	}else{
		return temphr;
	}

}	
}
public float s(){

	if(this.max()==0){return 0;}
	else 
	{return 1-(min()/max());}
}
public float v(){
	if(max()==0){return new Float(0.001).floatValue();}
	else {return max();}
	
}


public float sl(){
	if(this.max()==this.min()){return 0;}
	else if(this.l()>=0.5){return (this.max()-this.min())/(2*this.l());}
	else
	{return (this.max()-this.min())/(2-(2*this.l()));}
}

public float l(){
	if(max()==0){return new Float(0.001).floatValue();}
	else {return (max()+min())/2;}
	
}




//cartesian coordinates of HSV
public float x(){
	float h1= this.h();
	float s1= this.s();
return new Float((s1*Math.cos(Math.toRadians(h1)))).floatValue()*(v()/256);
	//return new Float((s1*Math.cos(Math.toRadians(h1)))).floatValue();
}
public float y(){
	float h1= this.h();
	float s1= this.s();
	return new Float((s1*Math.sin(Math.toRadians(h1)))).floatValue()*(v()/256);
	//return new Float((s1*Math.sin(Math.toRadians(h1)))).floatValue();

}
public float z(){
	return v();	
}

//cartesian coordinates of HSL
public float x1(){
	float h1= this.h();
	float s1= this.sl();
	

return new Float((s1*Math.cos(Math.toRadians(h1)))).floatValue()*(l()/256);
}
public float y1(){
	float h1= this.h();
	float s1= this.sl();

return new Float((s1*Math.sin(Math.toRadians(h1)))).floatValue()*(l()/256);
}

public float z1(){
return  l();
}

}//Class Ends here

