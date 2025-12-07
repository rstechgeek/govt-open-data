create table if not exists resource (
index_id varchar(100) primary key,
records jsonb not null
);

create or replace view view_orgs as 
select org_name, string_agg(index_id, ',') as index_ids 
from (
    select index_id, jsonb_array_elements_text(records->'org') as org_name 
    from resource 
    where jsonb_typeof(records->'org') = 'array'
) sub 
group by org_name;

create or replace view view_sectors as 
select sector_name, string_agg(index_id, ',') as index_ids 
from (
    select index_id, jsonb_array_elements_text(records->'sector') as sector_name 
    from resource
    where jsonb_typeof(records->'sector') = 'array'
) sub 
group by sector_name;


create table if not exists app_metadata (
    key varchar(100) primary key,
    value varchar(255)
);
