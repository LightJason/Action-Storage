/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.storage;

import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * removes all elements of the storage.
 * The action removes all elements of the storage
 * except the elements wich are forbidden
 *
 * {@code .storage/clear;}
 */
public final class CClear extends IBaseStorage
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -985959827654899897L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CClear.class, "storage" );

    /**
     * ctor
     */
    public CClear()
    {
        super();
    }

    /**
     * ctor
     *
     * @param p_resolver resolver function
     */
    public CClear( @Nonnull final Function<String, Boolean> p_resolver )
    {
        super( p_resolver );
    }

    /**
     * ctor
     *
     * @param p_forbidden forbidden keys
     */
    public CClear( @Nullable final String... p_forbidden )
    {
        super( p_forbidden );
    }

    /**
     * ctor
     *
     * @param p_fordbidden stream with borbidden keys
     */
    public CClear( @Nonnull final Stream<String> p_fordbidden )
    {
        super( p_fordbidden );
    }

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Nonnull
    @Override
    public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context,
                                           @Nonnull final List<ITerm> p_argument, @Nonnull final List<ITerm> p_return
    )
    {
        p_context.agent().storage().keySet().parallelStream()
                 .filter( i -> !m_resolver.apply( i ) )
                 .forEach( i -> p_context.agent().storage().remove( i ) );

        return Stream.empty();
    }

}
