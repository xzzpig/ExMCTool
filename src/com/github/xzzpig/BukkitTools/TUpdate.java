package com.github.xzzpig.BukkitTools;
import org.bukkit.plugin.*;

public class TUpdate
{
	public static String mess;
	
	public static String getNewestMessgae(Plugin plugin,String branch){
		try{
		String 
			auther = plugin.getDescription().getAuthors().get(0),
			pluginname = plugin.getName();
		String html = TUrl.getHtml("https://github.com/"+auther+"/"+pluginname+"/commits/"+branch);
		String uid = TString.sub(html,"<a href=\"/"+auther+"/"+pluginname+"/commit/","\" class=\"message\" data-pjax=\"true\" title=");
		return uid+"||"+TString.sub(html,"<a href=\"/"+auther+"/"+pluginname+"/commit/"+uid+"\" class=\"message\" data-pjax=\"true\" title=\"","</a>").split("\">")[0];
		}catch(Exception e){System.out.println(TString.Prefix(plugin.getName())+"获取更新失败");}return "";
	}
	
	public static boolean hasUpdate(Plugin plugin,String branch){
		String 
			cversin = plugin.getDescription().getVersion(),
			verson = getNewestVersion(plugin,branch);
		return !cversin.equalsIgnoreCase(verson);
	}
	
	public static String getNewestVersion(Plugin plugin,String branch){
		String 
			auther = plugin.getDescription().getAuthors().get(0),
			pluginname = plugin.getName();
		String html = TUrl.getHtml("https://github.com/"+auther+"/"+pluginname+"/blob/"+branch+"/src/plugin.yml");
		return TString.sub(html,"<td id=\"LC3\" class=\"blob-code blob-code-inner js-file-line\"><span class=\"pl-c1\"><span class=\"pl-ent\">version:</span> ","</span></td>");
	}
}
