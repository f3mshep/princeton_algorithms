class UF
  # these technically should be private
  # interesting to call nodes after calling other
  # methods to see how root changes data structure
  attr_accessor :nodes, :sizes, :largest

  def initialize(size)
    self.nodes = []
    self.sizes = []
    self.largest = []
    size.times do |node| 
      nodes << node
      sizes << 1
      largest << node
    end
  end

  def connected(p, q)
    # check if nodes connected by seeing if they share
    # a common root
    root(p) == root(q)
  end

  def union(p, q)
    # join 2 distinct nodes
    i = root(p)
    j = root(q)
    return if (i == j)
    if (sizes[i] < sizes[j])
      # if tree i is smaller than tree j,
      # i now points to j's root
      nodes[i] = j
      sizes[j] += sizes[i]
      largest[j] = [ largest[j], largest[i] ].max
    else
      nodes[j] = i
      sizes[i] += sizes[j]
      largest[i] = [ largest[j], largest[i] ].max
    end
  end

  def find(node)
    i = root(node)
    largest[i]
  end

  private

  def root(node)
    # get the root of the node
    while node != nodes[node]
      # make each node point to its grandparent
      # speeds up computation 
      nodes[node] = nodes[nodes[node]]
      node = nodes[node]
    end
    node
  end

end