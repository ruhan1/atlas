package org.commonjava.maven.atlas.spi.neo4j.effective.traverse;

import java.util.Iterator;
import java.util.Set;

import org.apache.maven.graph.effective.filter.ProjectRelationshipFilter;
import org.commonjava.maven.atlas.spi.neo4j.effective.NeoGraphSession;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;

@SuppressWarnings( "rawtypes" )
public class RootedNodesCollector
    extends AbstractAtlasCollector<Node>
{

    //    private final Logger logger = new Logger( getClass() );

    public Direction direction = Direction.OUTGOING;

    public RootedNodesCollector( final Node start, final NeoGraphSession session,
                                 final ProjectRelationshipFilter filter, final boolean checkExistence )
    {
        super( start, session, filter, checkExistence );
        logEnabled = true;
    }

    public RootedNodesCollector( final Set<Node> startNodes, final NeoGraphSession session,
                                 final ProjectRelationshipFilter filter, final boolean checkExistence )
    {
        super( startNodes, session, filter, checkExistence );
        logEnabled = true;
    }

    private RootedNodesCollector( final Set<Node> startNodes, final NeoGraphSession session,
                                  final ProjectRelationshipFilter filter, final boolean checkExistence,
                                  final Direction direction )
    {
        super( startNodes, session, filter, checkExistence, direction );
        logEnabled = true;
    }

    @Override
    public PathExpander reverse()
    {
        return new RootedNodesCollector( startNodes, session, filter, checkExistence, direction.reverse() );
    }

    public boolean hasFoundPaths()
    {
        return !found.isEmpty();
    }

    public Set<Node> getFoundNodes()
    {
        return found;
    }

    @Override
    public Iterator<Node> iterator()
    {
        return found.iterator();
    }

    @Override
    protected boolean returnChildren( final Path path )
    {
        if ( accept( path ) )
        {
            for ( final Node node : path.nodes() )
            {
                logger.info( "Adding node: %s", node );
                found.add( node );
            }
        }

        logger.info( "In any case, proceed on this path." );
        return true;
    }

}
