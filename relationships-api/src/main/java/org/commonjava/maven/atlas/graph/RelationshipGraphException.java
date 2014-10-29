/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.commonjava.maven.atlas.graph;

import java.util.IllegalFormatException;

public class RelationshipGraphException
    extends Exception
{

    private static final long serialVersionUID = 1L;

    private final Object[] params;

    public RelationshipGraphException( final String message, final Throwable error, final Object... params )
    {
        super( message, error );
        this.params = params;
    }

    public RelationshipGraphException( final String message, final Object... params )
    {
        super( message );
        this.params = params;
    }

    @Override
    public String getLocalizedMessage()
    {
        return getMessage();
    }

    @Override
    public String getMessage()
    {
        String message = super.getMessage();
        if ( params != null && params.length > 0 )
        {
            try
            {
                final String formatted = String.format( message.replaceAll( "\\{\\}", "%s" ), params );
                message = formatted;
            }
            catch ( final IllegalFormatException e )
            {
            }
        }

        return message;
    }
}
