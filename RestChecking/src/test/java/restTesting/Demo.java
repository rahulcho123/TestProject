package restTesting;
import org.hamcrest.core.Is;

import org.junit.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Demo 
{
public String url="https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22\r\n" ; 
		@Test
		public void demo1()
		{
		get(url).then().assertThat().body("city.name",Is.is("Munich"));	
		}
		
		@Test
		public void demo2()
		{
			get(url).then().and().body("cnt",Is.is(96));
		}
		@Test
		public void demo3() throws parseException
		{
			SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
			boolean isOneHourDiff=false;
			
			Response res=get(url);
			
			ArrayList<String>datesAndHours=res.then().extract().path("list.dt_txt");
			for(int i=0;i<datesAndHours.size()-1;i++)
			{
				String current=datesAndHours.get(i);
				String next= datesAndHours.get(i+1);
				
				Date d1=(Date) format.parse(current);
				Date d2=(Date) format.parse(next);
				
				long duration = d1.getTime()-d2.getTime();
				long diffInHours=TimeUnit.MILLISECONDS.toHours(duration);                                   
				
				if(diffInHours==-1)
				{
					isOneHourDiff=true;
				}
				else
				{
					isOneHourDiff=false;
					break;
				}
			}
			assertEquals(true,isOneHourDiff);
		}
		
		
		
		
		
		
}
