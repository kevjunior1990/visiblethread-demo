-- Insert Teams
INSERT INTO public.vt_team (name) VALUES ('Engineering'), ('Marketing');

-- Insert Users
INSERT INTO public.vt_user (email, creation_date, modification_date)
VALUES
    ('john.doe@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('jane.smith@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('alice.johnson@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('bob.williams@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('charlie.brown@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Associate Users with Teams
-- John & Jane → Engineering
-- Alice & Bob → Marketing
-- Charlie → Both teams
INSERT INTO public.vt_user_team (user_id, team_id)
VALUES
    ((SELECT id FROM public.vt_user WHERE email = 'john.doe@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Engineering')),
    ((SELECT id FROM public.vt_user WHERE email = 'jane.smith@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Engineering')),
    ((SELECT id FROM public.vt_user WHERE email = 'alice.johnson@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Marketing')),
    ((SELECT id FROM public.vt_user WHERE email = 'bob.williams@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Marketing')),
    ((SELECT id FROM public.vt_user WHERE email = 'charlie.brown@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Engineering')),
    ((SELECT id FROM public.vt_user WHERE email = 'charlie.brown@example.com'), (SELECT id FROM public.vt_team WHERE name = 'Marketing'));

-- Insert Documents
-- John, Jane, and Alice get 1 document each. Bob and Charlie do not have documents.
INSERT INTO public.vt_document (name, content, word_count, user_id, creation_date, modification_date)
VALUES
    ('project_plan.txt', 'This is a detailed project plan covering the next quarter. It includes major milestones, timelines, and deliverables. Each phase has been broken down into tasks with assigned responsibilities. Key stakeholders are engaged, and dependencies have been identified to ensure smooth execution.', 42,
     (SELECT id FROM public.vt_user WHERE email = 'john.doe@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('marketing_strategy.txt', 'The marketing strategy for Q2 includes a mix of social media campaigns, influencer partnerships, and SEO optimization. Our goal is to increase brand awareness by 20% and boost engagement through targeted ad placements and interactive content. The execution plan involves collaboration between content creators and analysts.', 47,
     (SELECT id FROM public.vt_user WHERE email = 'jane.smith@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('design_guidelines.txt', 'These design guidelines focus on consistency, usability, and accessibility. The document includes a color palette, typography, spacing rules, and interactive elements for UI components. These principles ensure that our design system remains cohesive and aligns with user expectations. Feedback loops are in place for iterations.', 46,
     (SELECT id FROM public.vt_user WHERE email = 'alice.johnson@example.com'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
