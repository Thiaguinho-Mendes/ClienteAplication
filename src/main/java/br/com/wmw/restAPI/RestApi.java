package br.com.wmw.restAPI;

import java.util.List;

import br.com.wmw.domain.Cliente;
import br.com.wmw.service.ClienteService;
import totalcross.io.ByteArrayStream;
import totalcross.io.IOException;
import totalcross.json.JSONFactory;
import totalcross.json.JSONObject;
import totalcross.net.HttpStream;
import totalcross.net.HttpStream.Options;
import totalcross.net.URI;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.MaterialWindow;
import totalcross.ui.Presenter;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.PressListener;

public class RestApi extends MaterialWindow {

	public static final String CONTENT_TYPE_JSON = "application/json";

	public RestApi() {
		super(false, new Presenter<Container>() {
			@Override
			public Container getView() {
				return new Container() {
					PressListener getPressListener(final String url, String httpType) {
						return (e) -> {
							HttpStream.Options options = new HttpStream.Options();
							options.httpType = httpType;
							HttpStream httpStream = null;
							ClienteService service = new ClienteService();
							
							try {
								httpStream = new HttpStream(new URI(url), options);
								ByteArrayStream bas = new ByteArrayStream(4096);
								bas.readFully(httpStream, 10, 2048);
								String data = new String(bas.getBuffer(), 0, bas.available());

								Response<Cliente> response = new Response<>();
								response.responseCode = httpStream.responseCode;

								if (HttpStream.GET.equals(httpType)) {
									response.listData = (JSONFactory.asList(data, Cliente.class));
									service.recebeDados(response);
									new MessageBox("Informação", "Dados recebido com sucesso!").popup();
								} else if (HttpStream.POST.equals(httpType)) {
									options.httpType = HttpStream.POST;
					                options.setContentType(CONTENT_TYPE_JSON);      
					                service.enviaDados(url, options);
                                    new MessageBox("Informação", "Dados enviados com sucesso!").popup();     
								}
								
							} catch (Exception ex) {
								new MessageBox("Erro", "Não foi possivel receber ou enviar dados: " + ex.getMessage()).popup();
							} finally {
								try {
									httpStream.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						};
					}

					@Override
					public void initUI() {
						String binUrl = "http://localhost:8080";
						
						Button btnGet = new Button("RECEBER DADOS");
						btnGet.addPressListener(getPressListener(binUrl + "/cliente", HttpStream.GET));
						add(btnGet, LEFT, AFTER, FILL, fmH * 3);

						Button btnPost = new Button("ENVIAR DADOS");
						btnPost.addPressListener(getPressListener(binUrl + "/cliente", HttpStream.POST));
						add(btnPost, LEFT, AFTER, FILL, fmH * 3);
					}
					};
			};
		});
	}

	public static class Response<T> {
		public T data;
		public List<T> listData;
		public int responseCode;
	}

	public static boolean checkClienteExists(String cpfCnpj) {
		String url = "http://localhost:8080/cliente";
	    JSONObject requestData = new JSONObject();
	    requestData.put("cpfCnpj", cpfCnpj);

	    HttpStream httpStream = null;
	    Options options = new Options();
	    options.httpType = HttpStream.GET;
	    options.data = requestData.toString();

	    try {
	    	httpStream = new HttpStream(new URI(url + "/exists/" + cpfCnpj), options);
	        String response = httpStream.readLine();

	        if("true".equals(response)) {
	        	return true;
	        } else {
	        	return false;
	        }
	    } catch (Exception ex) {
	        return false;
	    } finally {
	    	try {
				httpStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteClienteByWeb(String cpfCnpj) {
		String url = "http://localhost:8080/cliente";
		cpfCnpj.replaceAll("[^0-9]", "");
		 Options options = new Options();
		    options.httpType = HttpStream.DELETE;

		    try {
		        HttpStream httpStream = new HttpStream(new URI(url + "/delete/" + cpfCnpj), options);
		        httpStream.close();
		    } catch (Exception ex) {
		        System.err.println("Erro ao deletar o cliente: " + ex.getMessage());
		    }
	}

}
