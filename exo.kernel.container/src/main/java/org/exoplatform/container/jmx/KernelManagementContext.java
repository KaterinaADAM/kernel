/*
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.container.jmx;

import org.exoplatform.management.spi.ManagementProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class KernelManagementContext
{

   /** . */
   final ManagementContextImpl root;

   /** . */
   private final Set<ManagementProvider> providers;

   public KernelManagementContext()
   {
      this.root = new ManagementContextImpl(this);
      this.providers = new HashSet<ManagementProvider>();
   }

   public synchronized Collection<ManagementProvider> getProviders() {
      return providers;
   }

   public synchronized boolean addProvider(ManagementProvider provider)
   {
      // Prevent double registration just in case...
      if (providers.contains(provider)) {
         return false;
      }

      //
      providers.add(provider);

      // Perform registration of already registered managed components
      root.install(provider);

      //
      return false;
   }
}
