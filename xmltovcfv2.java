import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.*;
import java.util.*;
class vcf
{
String n;
List<String> no=new ArrayList<String>();
String fn;
String email;

}
class xmltovcf {
      Map< String,vcf> hm = new HashMap< String,vcf>();
      void xmlparse()
 {
try {
         File inputFile = new File("backupinfo.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.print("Root element: ");
         System.out.println(doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("contact");
         System.out.println("----------------------------");
System.out.println(nList.getLength());
         for (int temp = 0; temp < nList.getLength(); temp++) {
                        StringBuilder newcontact = new StringBuilder();
                        StringBuilder n=new StringBuilder();

                        Node nNode = nList.item(temp);



                       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                           Element eElement = (Element) nNode;

vcf v=new vcf();
                           newcontact.append(eElement.getElementsByTagName("displayName").item(0).getTextContent());
                           n.append(eElement.getElementsByTagName("displayName").item(0).getTextContent());
                           newcontact.append(eElement.getElementsByTagName("givenName").item(0).getTextContent()+";");

                           newcontact.append(eElement.getElementsByTagName("middleName").item(0).getTextContent()+";");
                           n.append(eElement.getElementsByTagName("familyName").item(0).getTextContent());

                           n.append(".vcf");
                           newcontact.append(eElement.getElementsByTagName("familyName").item(0).getTextContent()+";");

                            if(hm.containsKey(new String(n))==true)
                            v=hm.get(new String(n));
                            else
                            {v.n=new String(newcontact);
                            v.fn=new String(n);
                           hm.put(v.fn,v);
                                                       }
                           NodeList phoneNameList = eElement.getElementsByTagName("phone");

                           for (int count = 0; count < phoneNameList.getLength(); count++) {
                              Node node1 = phoneNameList.item(count);

                              if (node1.getNodeType() == node1.ELEMENT_NODE) {
                                 Element phone = (Element) node1;

                                 v.no.add(phone.getElementsByTagName("number").item(0).getTextContent());
                              }
                           }


}}



}catch (Exception e) {
          e.printStackTrace();
       }}
void vcfCreater()
{try{


File newfolder=new File("contact");
newfolder.mkdirs();



for (String name : hm.keySet())
        {

            vcf v = hm.get(name);
File f=new File("contact/".concat(v.fn));
             FileOutputStream fop=new FileOutputStream(f);
             if(f.exists())
  {fop.write("BEGIN:VCARD\nVERSION:2.1\nN:".getBytes());
  fop.write((v.fn+"\nFN:").getBytes());
  fop.write((v.n+"\n").getBytes());
  List<String> number=v.no;
  for(int i=0;i<v.no.size();i++)
  {
  fop.write(("TEL;Home:").getBytes());
  fop.write((number.get(i)+"\n").getBytes());
  }
  fop.write(("EMAIL:"+v.email).getBytes());
  fop.write("\nEND:VCARD".getBytes());
  }
 fop.flush();
   fop.close();
};
} catch (Exception e) {
            e.printStackTrace();
         }


}

 public static void main(String argv[])throws IOException {
 xmltovcf x=new xmltovcf();
 x.xmlparse();
 x.vcfCreater();
 }}
