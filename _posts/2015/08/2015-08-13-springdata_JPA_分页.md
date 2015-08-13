---
layout: post
title: 使用springdata JPA实现分页
---

<p>1.创建dao，继承JpaSpecificationExecutor和PagingAndSortingRepository。如下：</p>
<pre class="brush:java">


    public interface CodeEntryDao  extends JpaSpecificationExecutor<CodeEntry>,PagingAndSortingRepository<CodeEntry,Long> 
</pre>
<p>
   2.提供findAll接口，传入Specification 和Pageable 两个参数。如下：
</p>
<pre class="brush:java">


public Page<CodeEntry> findAll( Specification spec,Pageable pageRequest);
</pre>

<p>
    3.在service中调用findAll时创建Specification和Pageable,Specification是查询条件，Pageable包含pageNumber，pageSize，sort等参数。创建过程如下。
</p>
<pre class="brush:java">
    
    fff
        /**
         * 创建分页请求.
         */
        private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
            Sort sort = null;
            if ("auto".equals(sortType)) {
                sort = new Sort(Sort.Direction.DESC, "id");
            } else if ("id".equals(sortType)) {
                sort = new Sort(Sort.Direction.ASC, "entryId");
            }
    
            return new PageRequest(pageNumber - 1, pagzSize, sort);
        }
    
    
        /**
         * 创建动态查询条件组合.
         */
        private Specification<CodeEntry> buildSpecification(Map<String, Object> searchParams) {
            List< SearchFilter> filters = (List<SearchFilter>) SearchFilter.parse(searchParams);
            Specification<CodeEntry> spec = DynamicSpecifications.bySearchFilter(filters, CodeEntry.class);
            return spec;
        }
</pre>
<p>创建Specification时需要要有[searchFilter](http://github.com/lxp561784/lxp561784.github.com/tree/master/code)和[DynamicSpecifications](http://github.com/lxp561784/lxp561784.github.com/tree/master/code)类支持，点击去github克隆源码</p>