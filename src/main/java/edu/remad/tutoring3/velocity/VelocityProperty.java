package edu.remad.tutoring3.velocity;

/**
 * Velocity's properties
 * 
 * @author edu.remad
 * @since 2025
 */
public class VelocityProperty {

	private String resourceLoaderPath;
	private String suffix;
	private String layoutUrl;

	/**
	 * @return string-encoded resource loader path
	 */
	public String getResourceLoaderPath() {
		return resourceLoaderPath;
	}

	/**
	 * @param resourceLoaderPath resource loader path to set
	 */
	public void setResourceLoaderPath(String resourceLoaderPath) {
		this.resourceLoaderPath = resourceLoaderPath;
	}

	/**
	 * @return string-encoded suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return string-encoded layout url
	 */
	public String getLayoutUrl() {
		return layoutUrl;
	}

	/**
	 * @param layoutUrl layout url to set
	 */
	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}
}