/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.webservices.rest.web.resource.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * Search query used by the {@link SearchConfig}.
 * <p>
 * Typical usage involves:
 * <ol>
 * <li>Get an instance of the {@code Builder} via {@link Builder#Builder(String)}
 * <li>Set the various search query properties through the respective methods of the static builder
 * class ({@link Builder#withRequiredParameters(String...)},
 * {@link Builder#withOptionalParameters(String...)}.</li>
 * <li>Build the {@link SearchQuery} instance with the {@link Builder#build()} method.</li>
 * <li>Get the search query properties through the getter methods (such as
 * {@link #getRequiredParameters()} or {@link #getOptionalParameters()}).</li>
 * </ol>
 */
public class SearchQuery {
	
	private Set<String> requiredParameters;
	
	private Set<String> optionalParameters;
	
	private String description;
	
	private SearchQuery() {
	}
	
	public static class Builder {
		
		private SearchQuery searchQuery = new SearchQuery();
		
		/**
		 * Create an instance of the {@code Builder}.
		 * 
		 * @param description the search query description
		 */
		public Builder(String description) {
			searchQuery.description = description;
		}
		
		/**
		 * Set the {@code requiredParameters}.
		 * 
		 * @param requiredParameters the required parameters to be set
		 * @return this builder instance
		 * @should fail if required parameters are already set
		 */
		public Builder withRequiredParameters(String... requiredParameters) {
			if (searchQuery.requiredParameters != null) {
				throw new IllegalStateException("withRequiredParameters() must not be called twice");
			}
			
			searchQuery.requiredParameters = Collections.unmodifiableSet(new HashSet<String>(Arrays
			        .asList(requiredParameters)));
			return this;
		}
		
		/**
		 * Set the {@code optionalParameters}.
		 * 
		 * @param optionalParameters the optional parameters to be set
		 * @return this builder instance
		 * @should fail if optional parameters are already set
		 */
		public Builder withOptionalParameters(String... optionalParameters) {
			if (searchQuery.optionalParameters != null) {
				throw new IllegalStateException("withOptionalParameters() must not be called twice");
			}
			
			searchQuery.optionalParameters = Collections.unmodifiableSet(new HashSet<String>(Arrays
			        .asList(optionalParameters)));
			return this;
		}
		
		/**
		 * Builds an instance of {@code SearchQuery}.
		 * 
		 * @return a search query instance with properties set through the builder
		 * @should return a search query instance with properties set through the builder
		 * @should assign an empty set to required parameters if not set by the builder
		 * @should assign an empty set to optional parameters if not set by the builder
		 * @should fail if the description is null
		 * @should fail if the description is empty
		 * @should fail if both required and optional parameters are empty
		 */
		public SearchQuery build() {
			if (searchQuery.requiredParameters == null) {
				searchQuery.requiredParameters = Collections.emptySet();
			}
			if (searchQuery.optionalParameters == null) {
				searchQuery.optionalParameters = Collections.emptySet();
			}
			
			Validate.notEmpty(searchQuery.description, "Description must not be empty");
			Validate.isTrue(!searchQuery.requiredParameters.isEmpty() || !searchQuery.optionalParameters.isEmpty(),
			    "Either required or optional parameters must not be empty");
			return searchQuery;
		}
	}
	
	/**
	 * Get this {@code requiredParameters}.
	 * 
	 * @return this required parameters
	 */
	public Set<String> getRequiredParameters() {
		return requiredParameters;
	}
	
	/**
	 * Get this {@code optionalParameters}.
	 * 
	 * @return this optional parameters
	 */
	public Set<String> getOptionalParameters() {
		return optionalParameters;
	}
	
	/**
	 * Get this {@code description}.
	 * 
	 * @return this description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @see Object#hashCode()
	 * @return the hash code
	 * @should return same hashcode for equal search configs
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((optionalParameters == null) ? 0 : optionalParameters.hashCode());
		result = prime * result + ((requiredParameters == null) ? 0 : requiredParameters.hashCode());
		return result;
	}
	
	/**
	 * @see Object#equals(Object)
	 * @param obj the object to test for if equal to this
	 * @return true if obj is equal to this otherwise false
	 * @should return true if given this
	 * @should return true if this optional parameters and required parameters are equal to given
	 *         search query
	 * @should be symmetric
	 * @should be transitive
	 * @should return false if given null
	 * @should return false if given an object which is not an instanceof this class
	 * @should return false if this optional parameters is not equal to the given search queries
	 *         optional parameters
	 * @should return false if this required parameters is not equal to the given search queries
	 *         required parameters
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SearchQuery)) {
			return false;
		}
		SearchQuery other = (SearchQuery) obj;
		if (optionalParameters == null) {
			if (other.optionalParameters != null)
				return false;
		} else if (!optionalParameters.equals(other.optionalParameters))
			return false;
		if (requiredParameters == null) {
			if (other.requiredParameters != null)
				return false;
		} else if (!requiredParameters.equals(other.requiredParameters))
			return false;
		return true;
	}
}
